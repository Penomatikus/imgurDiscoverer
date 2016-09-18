package imgurDiscoverer.backend.net;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.frontent.componets.ImageBox;
import imgurDiscoverer.frontent.componets.ImageBoxArea;

public class DownloadManager extends SwingWorker<Void, ImageData>{
	
	private List<Downloader> downloaders;
	private static ImageBoxArea imageBoxArea;
	private static DownloadManager self;
	
	private DownloadManager() {
		this.downloaders = new ArrayList<>();
	}
	
	public static DownloadManager createDownloadManager() {
		return ( self == null ) ? new DownloadManager() : self;
	}
	
	public static void appendImageBoxArea(ImageBoxArea imageBoxArea) {
		DownloadManager.imageBoxArea =imageBoxArea;
	}

	@Override
	protected Void doInBackground() throws Exception {
		try {
			System.out.println("[ DownloadManager ] Prepare downloaders.");
			prepareDownloaders();
			System.out.println("[ DownloadManager ] Start downloaders.");
			startDownloaders();
			ProgramMonitor.setIsDownloadersAreRunning(true);
			System.out.println(Settings.toStaticString());
			while ( !isCancelled() ) {
				; // do nothing but wait
			}
			System.out.println("[ DownloadManager ] Stop downloaders.");
			stopDownloaders();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void process(List<ImageData> chunks) {
		super.process(chunks);
		for ( ImageData data : chunks ) {
			imageBoxArea.addBox(new ImageBox(data));
		}
			//System.out.println(String.copyValueOf(string));
	}
	
	
	@Override
	protected void done() {
		ProgramMonitor.setIsDownloadersAreRunning(false);
		System.out.println("[ DownloadManager ] I am done.");
	}
	
	private void prepareDownloaders() {
		int size = Settings.getProgramSettings().getThreads();
		for ( int i = 0; i < size; i++ )
			downloaders.add(new Downloader(this));
	}
	
	private void startDownloaders(){
		for ( Downloader d : downloaders ) {
			d.start();
		}
	}
	
	private void stopDownloaders(){
		for ( Downloader d : downloaders )
			d.cancel();
	}
	

}
