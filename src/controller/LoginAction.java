/*
 *Team5
 *Task 7
 *Jan 28,2015
 *Only for educational use
 */

package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import model.FlickrSearch;
import model.Model;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class LoginAction extends Action {

	public LoginAction(Model model) {
	}

	public String getName() {
		return "login.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
	    FlickrSearch fs = new FlickrSearch();
        ArrayList<String> rt = (ArrayList<String>) fs.run2();
        request.setAttribute("pictures", rt);
       
		if (request.getParameter("action") == null) return "login.jsp";
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("foWNuG5j0oJdoXoCktS8jdltP")
				.setOAuthConsumerSecret("O0oojri6fKpIbAo53ktesqInSnJbfoWYSzU7FAaeJxTPwfC9Yk");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		request.getSession().setAttribute("twitter", twitter);
		try {
			StringBuffer callbackURL = request.getRequestURL();
			int index = callbackURL.lastIndexOf("/");
			callbackURL.replace(index, callbackURL.length(), "").append("/callback");
			RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
			request.getSession().setAttribute("requestToken", requestToken);
			return requestToken.getAuthenticationURL();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		
		
		return "login.jsp";
	}
}