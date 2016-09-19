package imgurDiscoverer.backend.net;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.frontent.componets.ImageBox;
import imgurDiscoverer.frontent.componets.ImageBoxArea;
import imgurDiscoverer.frontent.componets.InformationPanel;

/**
 * Class providing an object to manage a bunch of {@link Downloader}s working
 * in the background, while the the EDT "will not be interrupted" ( this works, 
 * since the {@link DownloadManager} inherit {@link SwingWorker} ). <br> 
 * It will receive a list of {@link ImageData} objects from the {@link Downloader}s
 * and for each of those, it will create a new {@link ImageBox} out of the provided
 * content. In addition the {@link DownloadManager} will pass the new {@link ImageBox}s 
 * to the programs {@link ImageBoxArea}.<br>
 * <b>Usage: </b>
 * <pre>
 *  <code>
 *   Settings.createSettings(); // provide at least the default settings
 *   ProgramMonitor.createProgramMonitor; // create the ProgramMonitor;
 *   
 *   // start the DownloadManager
 *   myJButtonToStart.addActionListener( (e) -> {
 *      if ( !ProgramMonitor.isDownloadersAreRunning() ) 
 *         // myImageBoxArea is created else where in the code
 *         new DownloadManager(myImageBoxArea).execute();
 *      else
 *         System.exit(-1);
 *   });
 *   
 *   // stop the DownloadManager
 *   myJButtonToStop.addActionListener( (e) -> {
 *      DownloadManager.cancelDownloadProcess();
 *   });
 *  </code>
 * </pre>
 * 
 * The {@link DownloadManager} makes use of the classes {@link Settings} and 
 * {@link ProgramMonitor}. The first one is used to generate the wanted amount of 
 * {@link Downloader}s, where the second one will be used for cases, other 
 * program parts must know if the {@link DownloadManager} is running or not, without
 * passing the instance around the world. <br>
 * 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class DownloadManager extends SwingWorker<Void, ImageData>{
	
	/**
	 * Holds all {@link Downloader}s managed by the object
	 */
	private List<Downloader> downloaders;
	/**
	 * The {@link ImageBoxArea} to pass the generated {@link ImageBox}es to
	 */
	private ImageBoxArea imageBoxArea;
	/**
	 * A progress bar, to show the current download size
	 */
	private JProgressBar bar;
	/**
	 * The settings to use
	 */
	private Settings settings;
	/**
	 * Indicates if >> a << {@link DownloadManager} is running
	 */
	private static volatile boolean isRunning;
	
	/**
	 * Class providing an object to manage a bunch of {@link Downloader}s working
	 * in the background, while the the EDT "will not be interrupted" ( this works, 
	 * since the {@link DownloadManager} inherit {@link SwingWorker} ). <br> 
	 * It will receive a list of {@link ImageData} objects from the {@link Downloader}s
	 * and for each of those, it will create a new {@link ImageBox} out of the provided
	 * content. In addition the {@link DownloadManager} will pass the new {@link ImageBox}s 
	 * to the programs {@link ImageBoxArea}.<br>
	 * <b>Usage: </b>
	 * <pre>
	 *  <code>
	 *   Settings.createSettings(); // provide at least the default settings
	 *   ProgramMonitor.createProgramMonitor; // create the ProgramMonitor;
	 *   
	 *   // start the DownloadManager
	 *   myJButtonToStart.addActionListener( (e) -> {
	 *      if ( !ProgramMonitor.isDownloadersAreRunning() ) 
	 *         // myImageBoxArea is created else where in the code
	 *         new DownloadManager(myImageBoxArea).execute();
	 *      else
	 *         System.exit(-1);
	 *   });
	 *   
	 *   // stop the DownloadManager
	 *   myJButtonToStop.addActionListener( (e) -> {
	 *      DownloadManager.cancelDownloadProcess();
	 *   });
	 *  </code>
	 * </pre>
	 * 
	 * The {@link DownloadManager} makes use of the classes {@link Settings} and 
	 * {@link ProgramMonitor}. The first one is used to generate the wanted amount of 
	 * {@link Downloader}s, where the second one will be used for cases, other 
	 * program parts must know if the {@link DownloadManager} is running or not, without
	 * passing the instance around the world. <br>
	 *
	 * @param imageBoxArea	The {@link ImageBoxArea} to pass the generated {@link ImageBox}es to
	 */
	public DownloadManager(ImageBoxArea imageBoxArea ) {
		this.downloaders = new ArrayList<>();
		this.imageBoxArea = imageBoxArea;
		this.bar = InformationPanel.getBar();
		this.settings = Settings.createSettings();
		prepareBar();
	}
	
	private void prepareBar(){
		bar.setMinimum(0);
		bar.setMaximum(settings.getProgramSettings().getMaxMegabyte());
		bar.setStringPainted(true);
		bar.setString("0/ " + settings.getProgramSettings().getMaxMegabyte());
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		Settings settings = Settings.createSettings();
		DownloadManager.isRunning = true;
		ProgramMonitor.setIsDownloadersAreRunning(true);
		try {
			System.out.println("[ DownloadManager ] Prepare downloaders.");
			prepareDownloaders();
			System.out.println("[ DownloadManager ] Start downloaders.");
			startDownloaders();
			System.out.println(settings.toStaticString());
			while ( isRunning ); // wait until some one will change declaration "isRunning" 
			System.out.println("[ DownloadManager ] Stop downloaders.");
			stopDownloaders();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void process(List<ImageData> chunks) {
		for ( ImageData data : chunks ) {
			imageBoxArea.addBox(new ImageBox(data));
			ProgramMonitor.addDownloadedMegabyteAtRuntime(data.getFileSize());
			int barValue = (int) ProgramMonitor.getDownloadedMegabyteAtRuntime();
			int max = settings.getProgramSettings().getMaxMegabyte();
			bar.setValue(barValue);
			String barString = new DecimalFormat("####0.00").format(barValue) + "/"	+ max;
			bar.setString(barString);
			if ( barValue > max ) {
				DownloadManager.isRunning = false;
				bar.setString(barString + " ( letting downloaders work to end ");
			}
		}
	}
	
	
	@Override
	protected void done() {
		ProgramMonitor.setIsDownloadersAreRunning(false);
		System.out.println("[ DownloadManager ] I am done.");
	}
	
	/**
	 * Generates the amount of {@link Downloader}s provided 
	 * by the current {@link Settings}
	 */
	private void prepareDownloaders() {
		Settings settings = Settings.createSettings();
		int size = settings.getProgramSettings().getThreads();
		for ( int i = 0; i < size; i++ )
			downloaders.add(new Downloader(this));
	}
	
	/**
	 * Starts the {@link Downloader}s
	 */
	private void startDownloaders(){
		for ( Downloader d : downloaders )
			d.start();
	}
	
	/**
	 * Stops all running {@link Downloader}s
	 */
	private void stopDownloaders(){
		for ( Downloader d : downloaders )
			d.cancel();
	}
	
	/**
	 * Stops the {@link DownloadManager} which will result 
	 * in {@link DownloadManager#stopDownloaders()}
	 */
	public static void cancelDownloadProcess(){
		DownloadManager.isRunning = false;
	}
	
	/**
	 * @return {@link DownloadManager#isRunning}
	 */
	public static boolean isRunning(){
		return DownloadManager.isRunning;
	}
	

}
