package imgurDiscoverer.backend.net;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Provides an object, with the purpose to verify if an certain
 * image-hash does exist on the imgur server. <br>
 * Once the object checked the hash, the whole tested image URL, false or not, 
 * can be received via {@link URLValidator#getImageURL()}; <br>
 * 
 * <b>Usage:</b>
 * <pre>
 *  <code>
 *   char[] myHash = new char[]{ 'h', 'a', 'l', 'l', 'o' };
 *   URLValidator validator = new URLValidator();
 *   if ( validator.isValid( myHash )
 *   	URL url = validator.getImageURL();
 *  </code>
 * </pre> 
 * 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class URLValidator {
	
	/**
	 * The imgur link as String
	 */
	private static final String IMAGE_URL = "https://imgur.com/";
	
	/**
	 * The {@link URL} to connect with, looking for an image
	 */
	public URL url;
	
	/**
	 * Opens a connection to imgur for the hash and will analyze 
	 * the response code. As you can guess, the connection protocol 
	 * is HTTP, there with if the response code is not 200 the URL 
	 * is not valid. 
	 * @param hash	The generated hash which might result into an image on imgur
	 * @return	true if the status code is 200
	 */
	public boolean isValid(char[] hash){
		int statusCode = 0;
		try {
			url = new URL(IMAGE_URL + String.valueOf(hash));
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection ) url.openConnection();
			statusCode = con.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ( statusCode == 200 ) ? true : false;
	}
	
	/**
	 * @return {@link URLValidator#url}
	 */
	public URL getImageURL() {
		return url;
	}

}
