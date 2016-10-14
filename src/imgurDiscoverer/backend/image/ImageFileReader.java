package imgurDiscoverer.backend.image;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

/**
 * Provides an object for reading an image file from an input stream of
 * bytes and offering it as an {@link BufferedImage}. <br>
 * <b>Usage:</b>
 * <pre>
 *  <code>
 *    ImageFileReader reader = new ImageFileReader(myByteInputStream, "png");
 *    BufferedImage image = reader.getImage();
 *  </code>
 * <pre>
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class ImageFileReader {

	/**
	 * The generated image out of an {@link ByteInputStream}
	 */
	private BufferedImage image;
	
	/**
	 * Provides an object for reading an image file from an input stream of
	 * bytes and offering it as an {@link BufferedImage}. <br>
	 * <b>Usage:</b>
	 * <pre>
	 *  <code>
	 *    ImageFileReader reader = new ImageFileReader(myByteInputStream, "png");
	 *    BufferedImage image = reader.getImage();
	 *  </code>
	 * <pre>
	 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
	 *
	 */
	public ImageFileReader(ByteInputStream in, String fileExtension) throws Exception {
		readImage(in, fileExtension);
	}
	
	private void readImage(ByteInputStream in, String extension) throws Exception{
	    Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(extension);
	    ImageReader imageReader = readers.next();
	    ImageInputStream iis = ImageIO.createImageInputStream(in);
	    imageReader.setInput(iis, false);
	    int num = imageReader.getNumImages(true);
	    BufferedImage images[]= new BufferedImage[num];
	    for (int i = 0; i < num; ++i) 
	    	images[i] = imageReader.read(i);
	    if ( images.length > 1 )
	    	for (int i = 1; i <= images.length; i++)
	    		images[i].flush();
	    iis.flush();
	    iis.close();
	    image = images[0];
	}
	
	/**
	 * @return {@link ImageFileReader#image}
	 */
	public BufferedImage getImage(){
		return image;
	}
}
