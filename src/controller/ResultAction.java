package controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;

import model.CameraDAO;
import model.Model;
import model.TwitterFetcher;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

public class ResultAction extends Action{
	
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
        TwitterFetcher tf = new TwitterFetcher("foWNuG5j0oJdoXoCktS8jdltP", "O0oojri6fKpIbAo53ktesqInSnJbfoWYSzU7FAaeJxTPwfC9Yk");
        ArrayList<String> tweets = tf.getTweetsByWord("d7000");
        request.setAttribute("tweets", tweets);
		if (request.getParameter("action") == null) {
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");
        
        try {
            twitter.getOAuthAccessToken(requestToken, verifier);
            request.getSession().removeAttribute("requestToken");
        } catch (TwitterException e) {
        	e.printStackTrace();
        }
        	return "result.jsp";
		}
		
        try {
        	Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");	
			request.setCharacterEncoding("UTF-8");
	        String text = request.getParameter("text");
	        twitter = (Twitter)request.getSession().getAttribute("twitter");
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
