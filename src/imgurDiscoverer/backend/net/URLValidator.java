package imgurDiscoverer.backend.net;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Element;


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
	private static final String IMAGE_URL = "https://i.imgur.com/";
	
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
		boolean bool = false; 
		try {
			url = new URL(IMAGE_URL + String.valueOf(hash));
			HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			DocumentParser documentParser = new DocumentParser(connection);
//	    	documentParser.downloadAndParse(hash);
	    	url = documentParser.downloadAndParse(hash, connection); //documentParser.getImageURL();
//	    	con = (HttpURLConnection ) urlConnection;
//			con.setConnectTimeout(3000);
//			con.setReadTimeout(3000);
			bool = true;
//	    	DocumentParser documentParser = new DocumentParser(urlConnection);
//	    	documentParser.downloadAndParse(hash);
	    	
			//statusCode = con.getResponseCode();
		} catch (Exception e) {
			if ( !(e instanceof SocketTimeoutException) && !( e instanceof FileNotFoundException ))
				e.printStackTrace();
		}
		return bool; //( statusCode == 200 ) ? true : false;
	}
	
	/**
	 * @return {@link URLValidator#url}
	 */
	public URL getImageURL() throws NullPointerException  {
//		Element element = null;
//		URL directURL = null;
//		try {
//			element = Jsoup.parse(url, 1000).getElementsByAttributeValue("rel", "image_src").first();
//			if ( element != null ) {
//				directURL = new URL(element.attr("href").toString());
//				element = null;
//			}
//			else
//				throw new NullPointerException("There was no Element for image source at: " + url.toString());
//		} catch (Exception e) {
//			System.err.println("[Downloader] Could not receive direct image link ( " + url.toString() + ") ");
//			e.printStackTrace();
//		}
    	System.out.println(url.toString());
		return url;
	}

}
