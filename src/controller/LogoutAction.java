/*
 *Team5
 *Task 7
 *Jan 28,2015
 *Only for educational use
 */

package controller;

import javax.servlet.http.HttpServletRequest;

import model.Model;

/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class LogoutAction extends Action {

	public LogoutAction(Model model) { }

	public String getName() { return "logout.do"; }

	public String perform(HttpServletRequest request) {
        request.getSession().invalidate();

        return "login.do";
    }
}
