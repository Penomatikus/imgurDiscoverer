package imgurDiscoverer.backend.logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;

import imgurDiscoverer.backend.settings.Settings;

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
	private Image imageData; 
	/**
	 * The image name
	 */
	private String name;
	/**
	 * The file type
	 */
	private String extension;
	/**
	 * The file size of the image
	 */
	private double fileSize;
	/**
	 * The location of the image at download time
	 */
	private String fileLocationAtDownloadTime;
	/**
	 * The most common color of the passed image;
	 */
	private Color mostCommon;
	
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
	  *    data.getExtension();
	  *   </code>
	  *  </pre>
	 * @param imageData the image data as {@link BufferedImage}
	 * @param name the image name
	 */
	public ImageData(Image imageData, Color mostCommon, double fileSize, String name, String extension) {
		this.imageData = imageData;
		this.mostCommon = mostCommon;
		this.fileSize = fileSize;
		this.name = name;
		this.extension = extension; 
		Settings s = Settings.createSettings();
		fileLocationAtDownloadTime = s.getDirectorySettings().getPathForImages().getAbsolutePath() + 
				File.separator + name + "." + extension;
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
 	  *    data.getExtension(); // returns "none" as String
 	  *    data.getFileSize(); // returns 0
 	  *    data.getMostCommonColor(); // returns null
 	  *   </code>
 	  *  </pre>
	 * @param name the image name
	 */
	public ImageData(String name) {
		this(null, null, 0, name, "none");
	}
	
	/**
	 * @return {@link ImageData#imageData}
	 */
	public Image ugetImageData() {
		return imageData;
	}
	
	/**
	 * @return {@link ImageData#name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return {@link ImageData#extension}
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @return {@link ImageData#fileSize}
	 */
	public double getFileSize() {
		return fileSize;
	}
	
	/**
	 * @param size The calculated file size
	 */
	public void setFileSize(double size){
		this.fileSize = size;
	}
	
	/**
	 * @return {@link ImageData#fileLocationAtDownloadTime}
	 */
	public String getFileLocationAtDownloadTime(){
		return fileLocationAtDownloadTime;
	}
	
	/**
	 * Returns "/filename.jpg" or just "filename.jpg"
	 * @param andWithSeperator indicates if "/" should be added
	 * @return "/filename.jpg" or just "filename.jpg"
	 */
	public String getFileNameWithExtension(boolean andWithSeperator){
		String name = this.name + "." + extension;
		return ( andWithSeperator ) ? File.separator + name : name;
	}
	
	/**
	 * Flushes the buffer of the bufferedimage and sets it to null.
	 */
	public void release(){
		System.out.println("[ImageData] release memory in heap.");
		try {
			imageData.flush();
			imageData = null;
		} catch (Throwable e) {
			if ( !( e instanceof NullPointerException ) )
				e.printStackTrace();
		}
	}
	
	/**
	 * @return {@link ImageData#mostCommon}
	 */
	public Color getMostCommonColor(){
		return mostCommon;
	}
	
	/**
	 * 
	 * @return
	 */
	public VolatileImage createVolatileImage(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        VolatileImage vbimage = gc.createCompatibleVolatileImage(220,220);
        Graphics2D g2d = vbimage.createGraphics();
        g2d.drawImage(imageData, 0, 0, null );
        g2d.dispose();
        release();
        return vbimage;
	}
}
