package imgurDiscoverer.backend.net;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import imgurDiscoverer.backend.logic.HashGenerator;
import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.settings.Settings;

public class Downloader extends Thread {
	
	private boolean allowDownload;
	private File imagePath;
	private URLValidator urlValidator;
	private DownloadManager manager;
	private char[] hash;
	private HashGenerator generator; 
	private boolean isRunning;
	
	public Downloader(DownloadManager manager, Settings settings) {
		this.manager = manager;
		this.allowDownload = settings.getProgramSettings().isDownloadAllowed();
		this.imagePath = settings.getDirectorySettings().getPathForImages();
		this.urlValidator = new URLValidator();
		this.generator = new HashGenerator();
		this.isRunning = true;
	}
	
	@Override
	public void run() {
		
		while ( isRunning ) {
			// Ugly, however need this for SwingWorkers.process(List<T>  t)
			List<ImageData> tmp = new ArrayList<>(1);
			try {
				hash = generator.generateHash();
				if ( urlValidator.isValid( hash )) {
					if ( !allowDownload ) 
						tmp.add(new ImageData(String.valueOf(hash)));
				    else 
						tmp.add(downloadImage(urlValidator.getImageURL()));
					manager.process(tmp); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	private ImageData downloadImage(URL imageURL) throws IOException{
		Document doc = Jsoup.parse(imageURL, 3000);
		Element e = doc.getElementsByAttributeValue("rel", "image_src").first();
		String url = e.attr("href").toString(); 
		BufferedImage bufferedImage = null;
		try( InputStream in = new URL(e.attr("href").toString()).openStream() ) {
		    bufferedImage = ImageIO.read(in);
			String extension = url.split("\\.")[3].substring(0, 3);
			System.out.println("[Downloader] Getting image from " + imageURL.toString() + " to " + 
								imagePath.getAbsolutePath() + File.separator + String.valueOf(hash) + "." + extension );
			ImageIO.write(bufferedImage, extension,
					new File(imagePath.getAbsolutePath() + File.separator + String.valueOf(hash) + "." + extension));

		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		return new ImageData(bufferedImage, String.valueOf(hash));
	}
	
	/**
	 * Causes the {@link Downloader} to stop, however it waiting
	 * until all started processes are done.
	 */
	public void cancel(){
		this.isRunning = false;
	}
	
	

}
