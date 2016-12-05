package imgurDiscoverer.backend.net;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import imgurDiscoverer.backend.image.ImageColorPicker;
import imgurDiscoverer.backend.image.ImageDownScaler;
import imgurDiscoverer.backend.image.ImageFileReader;
import imgurDiscoverer.backend.logic.HashGenerator;
import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.monitoring.ProgramMonitor;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.backend.time.Stopwatch;

//service:jmx:rmi:///jndi/rmi://141.45.206.169:9010/jmxrmi
/**
 * 141.45.206.249
 * -Xms128m -Xmx325m -XX:GCTimeRatio=19 -XX:+UseParallelOldGC -XX:+DisableExplicitGC -XX:MaxGCPauseMillis=200 -XX:InitiatingHeapOccupancyPercent=0
 */

/**
 * Provides an object, to download an image from a specific Imgur URL.<br>
 * It will receive a hash from the {@link HashGenerator} and using a {@link URLValidator}
 * for checking, if an image with this hash exists on the Imgur server. Furthermore, 
 * in case the image exists, it will download the imgur preview pages HTML and extract the
 * direct path to the image. The path is used to download the image and storing it to the
 * user preferred folder for downloaded images. When the image is downloaded a new {@link ImageData}
 * object will be created and passed to the {@link DownloadManager} for processing it asynchronously 
 * to the EDT. <br> 
 * The downloader will call {@link Downloader#cancel()} by its self, if the preferred maximum of 
 * downloaded megabytes is reached.   <br><br>
 * <b>Usage:</b>
 * <pre>
 *  <code>
 *   Downloader downloader = new Downloader(this); // since this should be inside a DownloadManager
 *   downloader.start();
 *   // .... run until you want to stop it
 *   downloader.cancel();
 *   // Don't use: downloader.interrupt(); 
 *   // This will result in corrupted data since the downloader might still 
 *   // processing an image writing task. .cancel() will inform the Downloader to stop
 *   // after its current download / image processing task is finished
 *  </code
 * </pre>
 * <br>   
 * <b>Note:</b>
 * Since we don't know the file extension of the image and won't use time intensive imageIO
 * magic, I decided to download the preview HTML and extracting the extension out of the fetched
 * image path from the HTML.
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class Downloader implements Runnable {
	
	/**
	 * Class indicator for a running signal
	 */
	public static final short SIGNAL_RUN = 1;
	/**
	 * Class indicator for a stopping signal
	 */
	public static final short SIGNAL_STOP = 0;
	
	/**
	 * The {@link DownloadManager} for all {@link Downloader}s to use, 
	 * for passing the generated {@link ImageData} to
	 */
	private DownloadManager manager;
	/**
	 * Reference-number for all found images shared with all {@link Downloader}s
	 */
	private static volatile long downloadedFiles = 0;
	/**
	 * Indicates if the image of a valid hash should be downloaded or not
	 */
	private boolean notAllowDownload;
	/**
	 * The preferred path to save the images to
	 */
	private File imagePath;
	/**
	 * Needed for validating if the image hash will result in an image 
	 */
	private URLValidator urlValidator;
	/**
	 * Used to generate hashes
	 */
	private HashGenerator generator; 
	/**
	 * The generated hash to lookup
	 */
	private char[] hash;
	/**
	 * For catching the preferred settings to use
	 */
	private Settings settings;
	/**
	 * Holds the generated {@link ImageData} for passing it to {@link DownloadManager#process(List)}
	 */
	private List<ImageData> tmp;
	/**
	 * Holds all found hashes
	 */
	private List<char[]> foundHashes;
	/**
	 * Holds all not found hashes
	 */
	private List<char[]> notFoundHashes;
	
	/**
	 * Provides an object, to download an image from a specific Imgur URL.<br>
	 * It will receive a hash from the {@link HashGenerator} and using a {@link URLValidator}
	 * for checking, if an image with this hash exists on the Imgur server. Furthermore, 
	 * in case the image exists, it will download the imgur preview pages HTML and extract the
	 * direct path to the image. The path is used to download the image and storing it to the
	 * user preferred folder for downloaded images. When the image is downloaded a new {@link ImageData}
	 * object will be created and passed to the {@link DownloadManager} for processing it asynchronously 
	 * to the EDT. <br> 
	 * The downloader will call {@link Downloader#cancel()} by its self, if the preferred maximum of 
	 * downloaded megabytes is reached.  <br><br>
	 * <b>Usage:</b>
	 * <pre>
	 *  <code>
	 *   Downloader downloader = new Downloader(this); // since this should be inside a DownloadManager
	 *   downloader.start();
	 *   // .... run until you want to stop it
	 *   downloader.cancel();
	 *   // Don't use: downloader.interrupt(); 
	 *   // This will result in corrupted data since the downloader might still 
	 *   // processing an image writing task. .cancel() will inform the Downloader to stop
	 *   // after its current download / image processing task is finished
	 *  </code
	 * </pre>
	 * <br>   
	 * <b>Note:</b>
	 * Since we don't know the file extension of the image and won't use time intensive imageIO
	 * magic, I decided to download the preview HTML and extracting the extension out of the fetched
	 * image path from the HTML.
	 * @param manager
	 */
	public Downloader(DownloadManager manager) {
		System.out.println("created");
		Settings settings = Settings.createSettings();
		this.manager = manager;
		this.notAllowDownload = settings.getProgramSettings().isDownloadAllowed();
		this.imagePath = settings.getDirectorySettings().getPathForImages();
		this.urlValidator = new URLValidator();
		this.generator = new HashGenerator();
		this.settings = Settings.createSettings();
		this.foundHashes = new ArrayList<>(200);
		this.notFoundHashes = new ArrayList<>(200);
		ProgramMonitor.setRegisteredDownloaders(1);
	}
	
	/**
	 * While {@link ProgramMonitor#getDownloadedMegabyteAtRuntime()} is smaller then
	 * the user preferred maximum download size in MB, {@link Downloader#process()} will be 
	 * executed. If this becomes false, {@link Downloader#cancel()} will be called.
	 * However, before all this happens, a new {@link Stopwatch} will be executed, which will cause
	 * a tiny pause of two seconds for every loop. <br>
	 * After the {@link Downloader} was instructed to stop, it will unregister its self from 
	 * {@link ProgramMonitor#getRegisteredDownloaders()}
	 */
	@Override
	public void run() {
		boolean doFound = settings.getProgramSettings().isSaveFoundHashes();
		boolean doNotFound = settings.getProgramSettings().isSaveNotFoundHashes();
		long allowed = settings.getProgramSettings().getMaxMegabyte();
		
		while ( ProgramMonitor.getDownloadSignal() == SIGNAL_RUN ) {
			if ( ProgramMonitor.getNoRouteToHostExceptionCounter() < 50 ) {
				long current = (long) ProgramMonitor.getDownloadedMegabyteAtRuntime();
				if ( current < allowed )
					process();
				else 
					break;
			} else {
				break;
			}
		//	putHashesToLists(doFound, doNotFound);
		}
		
		ProgramMonitor.setRegisteredDownloaders( ProgramMonitor.getRegisteredDownloaders() - 1);
//		Thread.currentThread().interrupt();		
//		if ( Thread.interrupted() )
//			urlValidator.forceConnectionClose();
	}
	
	/**
	 * Initializes {@link Downloader#tmp} and generates a new hash, which will be
	 * checked by an {@link URLValidator} instance. The next step, is to check if
	 * the user preferred either do want to download the image behind the hash or
	 * not. <br>
	 * If he don't want, a new {@link ImageData} with only the String value of the hash
	 * will be added to {@link Downloader#tmp}.<br>
	 * If he do want, a new {@link ImageData} object will be returned from 
	 * {@link Downloader#downloadImage(URL)} and added to {@link Downloader#tmp}.
	 * Moreover, {@link ProgramMonitor#setAllDownloadedFiles(long)} will be increased by 
	 * one. <br>
	 * At least {@link Downloader#tmp} will passed to the {@link DownloadManager} for 
	 * processing the data.
	 */
	private void process(){
		tmp = new ArrayList<>(1);
		try {
			hash = generator.generateHash();
			if ( urlValidator.isValid( hash, notAllowDownload )) {
				if ( notAllowDownload ) 
					tmp.add(new ImageData(String.valueOf(hash)));
			    else
			    	tmp.add(downloadImage(urlValidator.getImageURL()));	
				push(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the current hash to the list of the found or not found hashes. 
	 * @param doFound		adds it to the dound list
	 * @param doNotFound	adds it to the not found list
	 */
	private void putHashesToLists(boolean doFound, boolean doNotFound) {
		if ( doFound )
			foundHashes.add(hash);
		if ( doNotFound )
			notFoundHashes.add(hash);
	}

	/**
	 * Calls {@link DownloadManager#process(List)} for passing the data to it 
	 * @param data	{@link Downloader#tmp}
	 */
	private void push(List<ImageData> data){
		manager.process(data); 
	};
	
	/**
	 * Opens a stream to the given {@link URL} and passes the received bytes into an 
	 * array. Out of this array a new {@link BufferedImage} will be created via 
	 * {@link Downloader#readImage(InputStream, String)}. <br>
	 * Next, it writes the {@link BufferedImage} to disk and calculates the file size
	 * of it. The file destination will be the user preferred path for the images. <br>
	 * At least the {@link BufferedImage} will be down scaled to 220px x 220px and passed
	 * to a new instance of {@link ImageData}, which then will be returned. <br>
	 * In case of any error, while processing the above steps, the method will just return 
	 * an instance of {@link ImageData} with the found valid hash.
	 * @param imageURL	the URL to download the images preview page from
	 * @return	new {@link ImageData} out of the collected downloaded image information
	 * @throws IOException	if the download failed or the image to disk process
	 */
	private ImageData downloadImage(URL imageURL) {
		System.out.println("Download image");
		BufferedImage bufferedImage = null;
		try( InputStream in = imageURL.openStream() ) {
			// Downloading part
			byte[] imageBytes = IOUtils.toByteArray(in);
			ByteInputStream byteInputStream = new ByteInputStream(imageBytes, imageBytes.length);
			String extension = imageURL.toString().split("\\.")[3].substring(0, 3);
			ImageFileReader imageFileReader = new ImageFileReader(byteInputStream, extension);
			bufferedImage = imageFileReader.getImage();
			ProgramMonitor.setAllDownloadedFiles(++downloadedFiles);
			byteInputStream.close();
			
			// File writing part
			File file = new File(imagePath.getAbsolutePath() + File.separator + String.valueOf(hash) + "." + extension);
			ImageIO.write(bufferedImage, "JPEG", file); // reduce the file size by 75% 
			double bytes = file.length();
			double kilobytes = (bytes / 1024);
			double megabytes = (kilobytes / 1024);
			double fileSize = megabytes;
			
			// Image scaling and ImageData-creating part
			ProgramMonitor.addDownloadedMegabyteAtRuntime(fileSize);
			BufferedImage thumb = ImageDownScaler.downScale(bufferedImage);
			ImageData data = new ImageData(ImageDownScaler.downScale(bufferedImage), 
										   ImageColorPicker.mostCommonColor(thumb), fileSize, String.valueOf(hash), extension);
			bufferedImage.flush();
			thumb.flush();
			imageBytes = null;
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ImageData(String.valueOf(hash));
	}
}
