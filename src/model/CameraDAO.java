/*Team 5
Task 7
Date: Jan. 28, 2015
Only for educational use
 */
package model;

import java.sql.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.CameraBean;

public class CameraDAO extends GenericDAO<CameraBean> {
	public CameraDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(CameraBean.class, tableName, pool);
	}	

	
	public  CameraBean[] readByCameraName(String name) throws RollbackException {
		try {
			Transaction.begin();
			CameraBean[] dbRecords = match(MatchArg.equals("Camera", name));
//			CameraBean dbRecord = null;
			if (dbRecords.length != 0) {
				Transaction.commit();
				return dbRecords;
			} 

			Transaction.commit();
			return null;
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		
	}
	public  int recordsByCameraAndDate(String name, Date date) throws RollbackException {
		try {
			Transaction.begin();
			CameraBean[] dbRecords = match(MatchArg.equals("camera", name),MatchArg.equals("date", date));
//			CameraBean dbRecord = null;
			
				Transaction.commit();
				return dbRecords.length;
		
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		
	}
}
