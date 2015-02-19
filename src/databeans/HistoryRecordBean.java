package databeans;


public class HistoryRecordBean {
		private String date;
		private String camera1Num;
		private String camera2Num;
		
		public String    getCamera1Num()  { return camera1Num;  }
		public String    getCamera2Num()  { return camera2Num;  }
		public String   getDate()       { return date;       }

		public void setCamera1Num(String i) 	      {	camera1Num  = i; }
		public void setCamera2Num(String i) 	      {	camera2Num  = i; }
	    public void setDate(String d)        		  { date   	   = d; }
	


}
