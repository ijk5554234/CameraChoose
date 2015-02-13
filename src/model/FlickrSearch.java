package model;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlickrSearch {
	
	public List<String> run(String key) {
		List<String> resultUrl = new ArrayList<String>();
		try {
			http("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=94757b1c87984c86802f38ad9ea125e5&tags="+key+"&format=json&nojsoncallback=1", resultUrl);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultUrl;
	}
	
    public static void http(String url, List<String> resultUrl) throws JSONException {

    	StringBuilder builder = new StringBuilder();
    	HttpClient client = new DefaultHttpClient();
    	HttpGet httpGet = new HttpGet(url);
    	try{
    		HttpResponse response = client.execute(httpGet);
    		StatusLine statusLine = response.getStatusLine();
    		int statusCode = statusLine.getStatusCode();
    		if(statusCode == 200){
    		
    		    System.out.println("get 200");
    		    HttpEntity e = response.getEntity();
    		    String s = EntityUtils.toString(e, "UTF-8");
    		    System.out.println(s);
    		    
    		    
    		    
    		    JSONObject obj = new JSONObject(s);
    		    JSONObject obj2 = (JSONObject) obj.get("photos");
    		    JSONArray obj3 = (JSONArray) obj2.get("photo");
    		    
    		    int photoNum = obj2.getInt("perpage");
    		    System.out.println("num >> " + photoNum);
    		    
    		    for(int i=0 ; i<photoNum ; i++) {
    		    	JSONObject photo = obj3.getJSONObject(i);
    		    	// String id = toString();
    		    	String id = photo.get("id").toString();
    		    	String secret = photo.get("secret").toString();
    		    	String server = photo.get("server").toString();
    		    	String farm = photo.get("farm").toString();
    		    	String photo_url = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
    		    	System.out.println("photo url >> " + photo_url);
    		    	resultUrl.add(photo_url);
    		    }
    		    
    		} else {
    			System.out.println("error");
    		}
    	}catch(ClientProtocolException e){
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	System.out.println("get: " + builder.toString());
    }
}