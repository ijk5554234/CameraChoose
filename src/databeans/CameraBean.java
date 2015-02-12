/*
Team 5
Task 7
Date: Jan. 28, 2015
Only for educational use
 */
package databeans;

import java.sql.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("recordId")
public class CameraBean {	
		private int recordId;
		private long twitterId;
		private String camera;
		private Date date;

		public int    getRecordId()  { return recordId;  }
		public long   getTwitterId() { return twitterId; }
		public String getCamera()    { return camera;    }
		public Date   getDate()      { return date;      }

		public void setRecordId(int i) 	      {	recordId  = i; }
		public void setTwitterId(long l) 	  {	twitterId = l; }
	    public void setCamera(String s) 	  {	camera    = s; }
	    public void setDate(Date d)           { date      = d; }
	}

