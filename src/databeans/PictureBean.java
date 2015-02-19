
package databeans;

public class PictureBean {	
		private String like;
		private String url;
		private String camera;
		private String model;

		public String getLike()   { return like;   }
		public String getModel()   { return model;   }
		public String getUrl() { return url; }
		public String getCamera()   { return camera;   }

		public void setLike(String i) 	  {	like   = i; }
		public void setUrl(String s) {	url = s; }
		public void setModel(String s) {	model = s; }
	    public void setCamera(String s)   {	camera   = s; }
	}

