package imgurDiscoverer.backend.logic;

import java.awt.image.BufferedImage;
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
	private BufferedImage imageData; 
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
	public ImageData(BufferedImage imageData, double fileSize, String name, String extension) {
		this.imageData = imageData;
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
 	  *   </code>
 	  *  </pre>
	 * @param name the image name
	 */
	public ImageData(String name) {
		this(null, 0, name, "none");
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
	
	public void release(){
		System.out.println("[ImageData] release memory in heap.");
		try {
			imageData.flush();
			imageData = null;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
