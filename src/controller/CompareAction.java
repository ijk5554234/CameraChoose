/*
 *Team5
 *Task 7
 *Jan 28,2015
 *Only for educational use
 */

package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FlickrSearch;
import model.Model;
import databeans.PictureBean;
import formbeans.CompareForm;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import twitter4j.Twitter;

public class CompareAction extends Action {
	private FormBeanFactory<CompareForm> formBeanFactory = FormBeanFactory.getInstance(CompareForm.class);

	public CompareAction(Model model) {
	}

	public String getName() {
		return "compare.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession();
		request.setAttribute("errors", errors);
		 if (session.getAttribute("oauth_verifier") == null)
			 session.setAttribute("oauth_verifier", request.getParameter("oauth_verifier"));
		try {
			CompareForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			Twitter twitter = (Twitter) request.getSession(false).getAttribute("twitter");
			if (twitter == null) {
				return "login.do";
			}

			if (!form.isPresent()) {
				return "compare.jsp";
			}

			errors.addAll(form.getValidationErrors());
			 if (errors.size() != 0) {
			 return "compare.jsp";
			 }



			PictureBean camera1 = new PictureBean();
			camera1.setCamera("camera1");
			camera1.setLike("0");
			camera1.setModel(form.getCamera1());

			PictureBean camera2 = new PictureBean();
			camera2.setCamera("camera2");
			camera2.setLike("0");
			camera2.setModel(form.getCamera2());
			
			System.out.println(camera1.getModel());
			System.out.println(camera2.getModel());

			FlickrSearch fs = new FlickrSearch();

			
			
			List<String> camera1url = fs.run(form.getCamera1().replace(" ", ""));
			List<String> camera2url = fs.run(form.getCamera2().replace(" ", ""));
			
			PictureBean[] cameralisttest = new PictureBean[10];
			Random rand = new Random();

			for (int i = 0; i < 5; i++) {
				int pic = rand.nextInt(camera1url.size());
				PictureBean cameratest = new PictureBean();
				cameratest.setCamera("camera1");
				cameratest.setLike("0");
				cameratest.setUrl(camera1url.get(pic));
				cameralisttest[i] = cameratest;
				camera1url.remove(pic);
			}
			for (int i = 5; i < 10; i++) {
				int pic = rand.nextInt(camera2url.size());
				PictureBean cameratest = new PictureBean();
				cameratest.setCamera("camera2");
				cameratest.setLike("0");
				cameratest.setUrl(camera2url.get(pic));
				cameralisttest[i] = cameratest;
				camera2url.remove(pic);
			}
			session.setAttribute("cameralist", cameralisttest);

			session.setAttribute("PictureBean1", camera1);
			session.setAttribute("PictureBean2", camera2);

			return "blind.do";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}