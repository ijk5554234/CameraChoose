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

public class CompareForm extends FormBean{
	private String camera1;
	private String camera2;
	private String action;
	
	public void setCamera1(String s) { camera1 = s; }
	public void setCamera2(String s) { camera2 = s; }

	public String getCamera1() { return camera1; }
	public String getCamera2() { return camera2; }


	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (camera1 == null || camera1.length() == 0)
			errors.add("Password is required");
		if (camera2 == null || camera2.length() == 0)
			errors.add("Password is required");
		if (errors.size() > 0)
			return errors;
		
		return errors;
	}
}
