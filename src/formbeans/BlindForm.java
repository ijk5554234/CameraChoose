/*
 *Team5
 *Task 7
 *Jan 28,2015
 *Only for educational use
 */

package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class BlindForm extends FormBean{
	private String url;
	private String camera;
	private String like;
	private String action;
	
	public void setURL(String s)    { url    = s; }
	public void setLike(String s)   { like   = s; }
	public void setCamera(String s) { camera = s; }
	public void setAction(String s) { action = s; }

	public String getURL()    { return url;    }
	public String getCamera() { return camera; }
	public String getLike()   { return like;   }
	public String getAction()   { return action;   }


	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (url == null || url.length() == 0)
			errors.add("EmailAddress is required");
		if (camera == null || camera.length() == 0)
			errors.add("Password is required");
		if (errors.size() > 0)
			return errors;
		
		return errors;
	}
}
