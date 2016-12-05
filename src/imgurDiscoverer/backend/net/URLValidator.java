package imgurDiscoverer.backend.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
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
	private static final String IMAGE_URL = "https://i.imgur.com/";
	
	/**
	 * The {@link URL} to connect with, looking for an image
	 */
	private URL url;
	/**
	 * The open connection to the imgur server
	 */
	private HttpURLConnection connection;
	/**
	 * Used to extract the image 
	 */
	private DocumentParser documentParser;
	
	/**
	 * Opens a connection to imgur for the hash and will analyze 
	 * the response code. As you can guess, the connection protocol 
	 * is HTTP, there with if the response code is not 200 the URL 
	 * is not valid. 
	 * @param hash	The generated hash which might result into an image on imgur
	 * @return	true if the status code is 200
	 */
	public boolean isValid(char[] hash, boolean doDownloadSource){
		boolean bool = false; 
		try {
			url = new URL(IMAGE_URL + String.valueOf(hash));
			connection = ( HttpURLConnection ) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
	    	if ( !doDownloadSource ) {
	    		documentParser = new DocumentParser();
	    		documentParser.downloadAndParse(hash, connection);
	    		url = documentParser.getImageURL();
	    	} 
	    	else {
	    		connection.connect();
	    	}
	    	connection.disconnect();
	    	url.toString(); // throws NullPointerExecption if the parsing was not succesfull.
			bool = true;
		} catch (Exception e) {
			if ( !(e instanceof SocketTimeoutException) && !(e instanceof FileNotFoundException))
				e.printStackTrace();
		}
		return bool; 
	}
	
	public void forceConnectionClose(){
		connection.disconnect();
	}
	
	/**
	 * @return {@link URLValidator#url}
	 * @throws IOException 
	 */
	public URL getImageURL() throws NullPointerException, IOException  {
		return url;
	}

}
