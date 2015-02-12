package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class TwitterFetcher extends WebAccessor {
	private final String twitterKey;
	private final String twitterSecret;

	public TwitterFetcher(String key, String secret) {
		twitterKey = key;
		twitterSecret = secret;
	}

	/**
	 * Generate Base64 code of keys for OAuth
	 */
	private String encodeKeys() {
		try {
			String encodedConsumerKey = URLEncoder.encode(twitterKey, "UTF-8");
			String encodedConsumerSecret = URLEncoder.encode(twitterSecret, "UTF-8");

			String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
			byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());

			return new String(encodedBytes);
		} catch (UnsupportedEncodingException e) {
			return new String();
		}
	}

	/**
	 * Application-Only Authentication
	 */
	public String getAuthenticationToken(String oauthUrl) throws IOException {
		HttpsURLConnection connection = null;
		String encodedCredentials = encodeKeys();

		try {
			URL url = new URL(oauthUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Host", "api.twitter.com");
			connection.setRequestProperty("User-Agent", "Jike Li");
			connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			connection.setRequestProperty("Content-Length", "29");
			connection.setUseCaches(false);

			writeRequest(connection, "grant_type=client_credentials");

			// Parse the JSON response into a JSON mapped object to fetch fields
			JSONObject obj = (JSONObject) JSONValue.parse(readResponse(connection));

			if (obj != null) {
				String tokenType = (String) obj.get("token_type");
				String token = (String) obj.get("access_token");

				return ((tokenType.equals("bearer")) && (token != null)) ? token : "";
			}
			return new String();
		} catch (MalformedURLException e) {
			throw new IOException("Invalid endpoint URL specified.", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * Fetch tweets
	 */
	public ArrayList<String> fetchTimelineTweets(String apiUrl) throws IOException {
		HttpsURLConnection connection = null;
		// get OAuth Token
		String token = getAuthenticationToken("https://api.twitter.com/oauth2/token");

		try {
			URL url = new URL(apiUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Host", "api.twitter.com");
			connection.setRequestProperty("User-Agent", "JikeLi");
			connection.setRequestProperty("Authorization", "Bearer " + token);
			connection.setUseCaches(false);

			JSONObject obj = (JSONObject) JSONValue.parse(readResponse(connection));

			JSONArray msg = (JSONArray) obj.get("statuses");
			Iterator<JSONObject> iterator = msg.iterator();

			ArrayList<String> resultArrayList = new ArrayList<String>();
			while (iterator.hasNext()) {
				JSONObject next = iterator.next();
				String text = (String) next.get("text");
				resultArrayList.add(text);
			}

			return resultArrayList;
		} catch (MalformedURLException e) {
			throw new IOException("Invalid endpoint URL specified.", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public void fetchTweetsExample() throws IOException {
		String queryUrlString = "https://api.twitter.com/1.1/search/tweets.json?q=" + URLEncoder.encode("#iphone", "UTF-8") + "&count=" + 5
				+ "&result_type=popular";
		System.out.println(fetchTimelineTweets(queryUrlString));

	}

	public ArrayList<String> getTweetsByWord(String s) {
		String queryUrlString;
		ArrayList<String> result = null;
		try {
			queryUrlString = "https://api.twitter.com/1.1/search/tweets.json?q=" + URLEncoder.encode(s, "UTF-8") + "&count=" + 5
					+ "&result_type=popular";
			 result = fetchTimelineTweets(queryUrlString);

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		TwitterFetcher t = new TwitterFetcher("foWNuG5j0oJdoXoCktS8jdltP", "O0oojri6fKpIbAo53ktesqInSnJbfoWYSzU7FAaeJxTPwfC9Yk");
//		ArrayList<String> s = t.getTweetsByWord("d7000");
//		for (String str : s) System.out.println(str);
//
//	}
}