package imgurDiscoverer.backend.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides an object, which will download a web document
 * and parsing it to find the correct imgur image link. <br>
 * <b>Note: </b> This parser does not fit in any definition of
 * a professional "parser". However, with that name the purpose of this
 * class should be clear. <br> 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class DocumentParser {

	/**
	 * Holds the open connection to the server
	 */
	private HttpURLConnection urlConnection;
	/**
	 * The found image url;
	 */
	private URL imageURL;
	
	/**
	 * Provides an object, which will download a web document
	 * and parsing it to find the correct imgur image link. <br>
	 * <b>Note: </b> This parser does not fit in any definition of
	 * a professional "parser". However, with that name the purpose of this
	 * class should be clear. <br> 
	 * @param urlconnection The open connection to the host
	 */
	public DocumentParser(HttpURLConnection urlConnection) {
		this.urlConnection = urlConnection;
	}

	public URL downloadAndParse(char[] hash, HttpURLConnection urlConnection) throws IOException {
		String line = "";
		Pattern pattern = Pattern.compile("http://i.imgur.com/" + String.valueOf(hash) + ".(jpg|png|gif)");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
													urlConnection.getInputStream()));
		URL imageURL = null;
		Matcher matcher = null;
		do {
			line = reader.readLine();
		    matcher = pattern.matcher(line);
		    if (matcher.find() ) {
		    	imageURL = new URL(matcher.group(0));
		     	break;
		    }
		} while ( (line) != null );
		pattern = null;
		line = null;
		reader.close();
		urlConnection.disconnect();
		return imageURL;
		
	}
	
	/**
	 * @return {@link DocumentParser#imageURL}
	 */
	public URL getImageURL(){
		return imageURL;
	}
}
