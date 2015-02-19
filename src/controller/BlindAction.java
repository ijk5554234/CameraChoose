/*
 *Team5
 *Task 7
 *Jan 28,2015
 *Only for educational use
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import databeans.PictureBean;
import formbeans.BlindForm;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import twitter4j.Twitter;

public class BlindAction extends Action {
	private FormBeanFactory<BlindForm> formBeanFactory = FormBeanFactory.getInstance(BlindForm.class);

	public BlindAction(Model model) {
	}

	public String getName() {
		return "blind.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			BlindForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			Twitter twitter = (Twitter) request.getSession(false).getAttribute("twitter");

			if (twitter == null) {
				return "login.do";
			}

			errors.addAll(form.getValidationErrors());
			// if (errors.size() != 0) {
			// return "blind.jsp";
			// }

			PictureBean camera1 = (PictureBean) request.getSession(false).getAttribute("PictureBean1");
			PictureBean camera2 = (PictureBean) request.getSession(false).getAttribute("PictureBean2");

			HttpSession session = request.getSession();
			if (camera1.getCamera().equals(form.getCamera()) && form.getAction().equals("like")) {
				int count = Integer.parseInt(camera1.getLike());
				count++;
				camera1.setLike(count + "");
				session.setAttribute("PictureBean1", camera1);
			} else if (camera2.getCamera().equals(form.getCamera()) && form.getAction().equals("like")) {
				int count = Integer.parseInt(camera2.getLike());
				count++;
				camera2.setLike(count + "");
				session.setAttribute("PictureBean2", camera2);
			}

			// Attach (this copy of) the user bean to the session

			PictureBean[] cameralist = (PictureBean[]) request.getSession(false).getAttribute("cameralist");

			if (cameralist.length == 0 || cameralist == null) {
				return "result.do";
			}

			// create a random index of pic
			int pic = 0;
			pic = (int) (Math.random() * cameralist.length - 1);
			PictureBean topagepic = cameralist[pic];// take one to page
			request.setAttribute("picture", topagepic);
			PictureBean[] cameralistnew = new PictureBean[cameralist.length - 1];
			int n = 0;
			for (int i = 0; i < cameralist.length; i++) {
				if (i == pic) continue;
				cameralistnew[n] = cameralist[i];
				n++;
			}

			session.setAttribute("cameralist", cameralistnew);
			return "blind.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}