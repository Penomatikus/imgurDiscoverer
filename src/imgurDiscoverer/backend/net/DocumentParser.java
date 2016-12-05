package imgurDiscoverer.backend.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import imgurDiscoverer.backend.monitoring.ProgramMonitor;

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
	 * The found image URL;
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
	public DocumentParser() { }

	/**
	 * Opens a new {@link ReadableByteChannel} from the connection and storing
	 * a total amount of 8300 bytes into a {@link ByteBuffer} out of its {@link InputStream}. <br>
	 * This corresponds approximately to 1/8 of the total amount of bytes the hole
	 * HTML document consists of. <br>
	 * However, this method of downloading just 1/8 HTML document is done due to performance and memory management.
	 * The total document would have a size of around 62773 bytes but the information we need is placed
	 * in the first 8400 one. <br>
	 * In addition, after the buffer reached it's allocated size, the last 400 bytes of the 
	 * downloaded bytes will be cloned into a new byte array. <br>
	 * This part contains the information about the image link and is passed as new string
	 * to a {@link Pattern} / {@link Matcher} combination to get this link. Parsing a 400 byte instead of 
	 * 8400 byte for an URL, increases the performance and memory management slightly. 
	 * @param hash	The hash of the image 
	 * @param urlConnection	The connection object
	 * @throws IOException If an error occurred while extracting the image link.
	 */
	public void downloadAndParse(char[] hash, HttpURLConnection connection) throws IOException {
		ReadableByteChannel channel = null;
		InputStream stream = null;
		boolean success = false;
		try {
			stream = connection.getInputStream();
			channel = Channels.newChannel(stream);
			success = true;
		} catch (Exception e) {
			
			if ( !(e instanceof NullPointerException) && !(e instanceof FileNotFoundException))
				e.printStackTrace();
			if ( e instanceof NoRouteToHostException )
				ProgramMonitor.setNoRouteToHostExceptionCounter(
						ProgramMonitor.getNoRouteToHostExceptionCounter() + 1);
		} 
		
		if ( success )
			download(channel, hash);
		
	}

	private void download(ReadableByteChannel channel, char[] hash) throws IOException{
		int allocatedSize = 8400;
		ByteBuffer buffer = ByteBuffer.allocate(allocatedSize);
		while ( buffer.hasRemaining() )
				channel.read(buffer);
		channel.close();
		byte[] data = buffer.array();
		byte[] subData = new byte[500];
		buffer.clear();
		for ( int i = 7900, j = 0; i < allocatedSize; i++, j++ )
			subData[j] = data[i];
		
		String dataString = new String(data);
		Pattern pattern = Pattern.compile("http://i.imgur.com/" + String.valueOf(hash) + ".(jpg|png|gif)");
		Matcher matcher = pattern.matcher(dataString);
		if ( matcher.find() ) {
			imageURL = new URL(matcher.group());
			data = null;
			subData = null;
		}
		else {
			data = null;
			subData = null;
		}		
	}

	/**
	 * @return {@link DocumentParser#imageURL}
	 */
	public URL getImageURL(){
		return imageURL;
	}
}
