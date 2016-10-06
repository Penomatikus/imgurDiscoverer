package imgurDiscoverer.backend.net;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.IOUtils;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import imgurDiscoverer.backend.logic.HashGenerator;
import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.backend.view.ImageDownScaler;


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
public class Downloader extends Thread {
	
	/**
	 * The {@link DownloadManager} for all {@link Downloader}s to use, 
	 * for passing the generated {@link ImageData} to
	 */
	private static volatile DownloadManager manager;
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
	 * Indicates if the {@link Downloader} is still processing a download
	 * or creating the {@link ImageData}
	 */
	private boolean isRunning;
	/**
	 * For catching the preferred settings to use
	 */
	private Settings settings;
	/**
	 * Holds the generated {@link ImageData} for passing it to {@link DownloadManager#process(List)}
	 */
	private List<ImageData> tmp;
	
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
		Settings settings = Settings.createSettings();
		Downloader.manager = manager;
		this.notAllowDownload = settings.getProgramSettings().isDownloadAllowed();
		this.imagePath = settings.getDirectorySettings().getPathForImages();
		this.urlValidator = new URLValidator();
		this.generator = new HashGenerator();
		this.isRunning = true;
		this.settings = Settings.createSettings();
	}
	
	@Override
	public void run() {
		while ( isRunning ) {
			long current = (long) ProgramMonitor.getDownloadedMegabyteAtRuntime();
			long allowed = settings.getProgramSettings().getMaxMegabyte();
			if ( current < allowed )
				process();
			else
				cancel();
		}
		ProgramMonitor.setRegisteredDownloaders(
				ProgramMonitor.getRegisteredDownloaders() - 1);
	}
	
	/**
	 * Initializes {@link Downloader#tmp} and generates a new hash, which will be
	 * checked by an {@link URLValidator}. If the hash is valid, {@link Downloader#downloadImage(URL)}
	 * is called. At last, {@link Downloader#notAllowDownload} will be incremented and passed 
	 * to the {@link ProgramMonitor} and {@link Downloader#push(List)} is called to push 
	 * {@link Downloader#tmp}, now with the {@link ImageData}, to the {@link DownloadManager}. 
	 */
	private void process(){
		tmp = new ArrayList<>(1);
		try {
			hash = generator.generateHash();
			if ( urlValidator.isValid( hash )) {
				if ( notAllowDownload ) 
					tmp.add(new ImageData(String.valueOf(hash)));
			    else 
					tmp.add(downloadImage(urlValidator.getImageURL()));	
				ProgramMonitor.setAllDownloadedFiles(++downloadedFiles);
				push(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls {@link DownloadManager#process(List)} for passing the data to it 
	 * @param data	{@link Downloader#tmp}
	 */
	private static synchronized void push(List<ImageData> data){
		manager.process(data); 
	};
	
	/**
	 * Downloads the preview HTML page of the image and extracts the direct image path, 
	 * the image is downloaded from. It will get the image height and width, its file size 
	 * and its extension. 
	 * @param imageURL	the URL to download the images preview page from
	 * @return	new {@link ImageData} out of the collected downloaded image information
	 * @throws IOException	if the download failed or the image to disk process
	 */
	private ImageData downloadImage(URL imageURL) throws IOException{
		BufferedImage bufferedImage = null;
		try( InputStream in = imageURL.openStream() ) {
			byte[] imageBytes = IOUtils.toByteArray(in);
			ByteInputStream byteInputStream = new ByteInputStream(imageBytes, imageBytes.length);
			String extension = imageURL.toString().split("\\.")[3].substring(0, 3);
			bufferedImage = readImage(byteInputStream, extension)[0];
			byteInputStream.close();
			ImageDownScaler downScaler = new ImageDownScaler(bufferedImage);
			ImageData data = new ImageData(downScaler.downScale(), 0 ,String.valueOf(hash), "");
			downScaler.release();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ImageData(String.valueOf(hash));
		//return (data != null ) ? data : ( data = new ImageData(String.copyValueOf(hash) ) );
	}
//	private ImageData downloadImage(URL imageURL){
//		String extension = "";
//		try ( ImageInputStream stream = ImageIO.createImageInputStream(
//				imageURL.openStream())){
//			Iterator<ImageReader> iterator = ImageIO.getImageReaders(stream);
//			if ( !iterator.hasNext() )
//				return new ImageData(String.copyValueOf(hash));
//			ImageReader reader = (ImageReader) iterator.next();
//			reader.getDefaultReadParam(); 
//			reader.setInput(stream, true, true);
//			ImageReadParam param = new ImageReadParam();
//			
//			param.setSourceSubsampling(220, 200, 0, 0);
//			bufferedImage = reader.read(0);
//			extension = reader.getFormatName();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ImageData(bufferedImage, 0, String.valueOf(hash), extension);
//	}
	
	
	private BufferedImage[] readImage(InputStream in, String extension) throws Exception{
	    Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(extension);
	    ImageReader imageReader = (ImageReader) readers.next();
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
	    return images;
	}
	
	/**
	 * Causes the {@link Downloader} to stop, however it waiting
	 * until all started processes are done.
	 */
	public void cancel(){
		this.isRunning = false;
	}
	
	

}
