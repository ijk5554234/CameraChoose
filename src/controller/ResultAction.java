package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import model.CameraDAO;
import model.GoogleSearcher;
import model.Model;
import model.TwitterFetcher;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;
import databeans.CameraBean;
import databeans.HistoryRecordBean;
import databeans.PictureBean;

public class ResultAction extends Action {

	CameraDAO cameraDAO;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "result.do";
	}

	public ResultAction(Model model) {
		cameraDAO = model.getCameraDAO();
	}

	@Override
	public String perform(HttpServletRequest request) {

		// get the camera result
		PictureBean c1 = (PictureBean) request.getSession().getAttribute("PictureBean1");
		PictureBean c2 = (PictureBean) request.getSession().getAttribute("PictureBean2");
		PictureBean camera = c1;
		if (Integer.parseInt(c2.getLike()) >= Integer.parseInt(c1.getLike())) camera = c2;
		request.getSession().setAttribute("camera", camera);
		try {
			// Get compare history
			CameraBean cb = new CameraBean();
			cb.setCamera(camera.getModel());
			java.sql.Date dt = new java.sql.Date(new java.util.Date().getTime()); 
			cb.setDate(dt);
			cameraDAO.create(cb);
			CameraBean[] camera1 = cameraDAO.readByCameraName(c1.getModel());
			CameraBean[] camera2 = cameraDAO.readByCameraName(c2.getModel());
			
			if (camera1 != null && camera2 != null && camera1.length > 0 && camera2.length > 0) {
				int camera1Times = camera1.length;
				int camera2Times = camera2.length;
				request.setAttribute("camera1name", c1.getModel());
				request.setAttribute("camera2name", c2.getModel());
				request.setAttribute("camera1Times", camera1Times);
				request.setAttribute("camera2Times", camera2Times);
				
				
				try {
					double camera1SearchNum = GoogleSearcher.googleSearch(c1.getModel());
					double camera2SearchNum = GoogleSearcher.googleSearch(c2.getModel());
			    request.setAttribute("camera1SearchNum", camera1SearchNum);
			    request.setAttribute("camera2SearchNum", camera2SearchNum);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int dateNum = 0;
				for (int i = 0; i < camera1.length; i++) {
					if (i >= 1 && camera1[i].getDate().equals(camera1[i - 1].getDate())) {
						continue;
					} else {
						dateNum++;
					}
				}
				HistoryRecordBean[] records = new HistoryRecordBean[dateNum];
				int j = 0;
				for (int i = 0; i < camera1.length; i++) {
					if (!(i >= 1 && camera1[i].getDate().equals(camera1[i - 1].getDate()))) {
						int record1Num = cameraDAO.recordsByCameraAndDate(c1.getModel(), camera1[i].getDate());
						int record2Num = cameraDAO.recordsByCameraAndDate(c2.getModel(), camera1[i].getDate());
						records[j] = new HistoryRecordBean();
						SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
						String date = df.format(camera1[i].getDate());
						records[j].setDate(date);
						records[j].setCamera1Num(String.valueOf(record1Num));
						records[j].setCamera2Num(String.valueOf(record2Num));
						j++;
					}
				}
				request.setAttribute("DateNum", dateNum);
				request.setAttribute("DateRecords", records);
			}
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Fetch the twitter timeline
		TwitterFetcher tf = new TwitterFetcher("foWNuG5j0oJdoXoCktS8jdltP", "O0oojri6fKpIbAo53ktesqInSnJbfoWYSzU7FAaeJxTPwfC9Yk");
		ArrayList<String> tweets = tf.getTweetsByWord(camera.getModel());
		request.setAttribute("tweets", tweets);

		if (request.getParameter("action") == null) {
			return "result.jsp";
		}

		try {	
			Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
			RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
			String verifier = (String) request.getSession().getAttribute("oauth_verifier");
			try {
				twitter.getOAuthAccessToken(requestToken, verifier);
			} catch (TwitterException e) {
				return "error.jsp";
			}
			
			request.setCharacterEncoding("UTF-8");
			String text = request.getParameter("text");
			twitter = (Twitter) request.getSession().getAttribute("twitter");
			twitter.updateStatus(text);
			request.setAttribute("msg", "Result has been tweeted");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			request.setAttribute("msg", "You have already shared your result");
		}

		return "result.jsp";
	}

}
