/*
Team 5
Task 7
Date: Jan. 28, 2015
Only for educational use
 */
package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;


public class Model {
	private CameraDAO cameraDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL = config.getInitParameter("jdbcURL");

			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			cameraDAO = new CameraDAO("camera", pool);
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

	public CameraDAO getCameraDAO() {
		return cameraDAO;
	}
}
