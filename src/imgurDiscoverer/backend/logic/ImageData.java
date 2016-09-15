package imgurDiscoverer.backend.logic;

import java.awt.image.BufferedImage;

/**
 * Provides an object for holding image information. <br>
 * <li> A {@link BufferedImage} describes the image
 * <li> A {@link String} for the name <br><br>
 * <b>Usage:</b>
 *  <pre>
 *   <code>
 *    ImageData data = new ImageData("myImage");
 *    data.getName();
 *    data.getImageData(); // returns null cause no BufferedImage was set
 *   </code>
 *  </pre>
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public final class ImageData {
	
	/**
	 *  The image data as {@link BufferedImage}
	 */
	private BufferedImage imageData; 
	/**
	 * The image name
	 */
	private String name;
	
	/**
	  * Provides an object for holding image information. <br>
	  * <li> A {@link BufferedImage} describes the image
	  * <li> A {@link String} for the name <br><br>
	  * <b>Usage:</b>
	  *  <pre>
	  *   <code>
	  *    ImageData data = new ImageData("myImage");
	  *    data.getName();
	  *    data.getImageData();
	  *   </code>
	  *  </pre>
	 * @param imageData the image data as {@link BufferedImage}
	 * @param name the image name
	 */
	public ImageData(BufferedImage imageData, String name) {
		this.imageData = imageData;
		this.name = name;
	}
	
	/**
	  * Provides an object for holding image information. <br>
	  * <li> A {@link BufferedImage} describes the image
	  * <li> A {@link String} for the name <br><br>
	  * <b>Usage:</b>
	  *  <pre>
 	  *   <code>
 	  *    ImageData data = new ImageData("myImage");
 	  *    data.getName();
 	  *    data.getImageData(); // returns null cause no BufferedImage was set
 	  *   </code>
 	  *  </pre>
	 * @param name the image name
	 */
	public ImageData(String name) {
		this(null, name);
	}
	
	/**
	 * @return {@link ImageData#imageData}
	 */
	public BufferedImage getImageData() {
		return imageData;
	}
	
	/**
	 * @return {@link ImageData#name}
	 */
	public String getName() {
		return name;
	}

	
	

}
