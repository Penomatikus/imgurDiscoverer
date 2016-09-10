package imgurDiscoverer.backend.net;

import java.net.HttpURLConnection;
import java.net.URL;

public class URLValidator {
	
	private static final String imgurURL = "https://i.imgur.com/";
	private static final String filetype = ".jpg";
	
	public boolean isValid(char[] hash){
		int statusCode = 0;
		try {
			URL url = new URL(imgurURL + String.valueOf(hash) + filetype);
			statusCode = ((HttpURLConnection) url.openConnection()).getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ( statusCode == 200 ) ? true : false;
	}

}
