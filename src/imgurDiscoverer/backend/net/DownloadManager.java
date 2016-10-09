package imgurDiscoverer.backend.net;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
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
	 * Indicates the number of total images
	 */
	private static int totalNumber;
	/**
	 * Shows current task of the download manager
	 */
	private JLabel currentTask;
	/**
	 * Shows the current progressed images
	 */
	private JLabel downloadedImages;
	
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
	public DownloadManager(ImageBoxArea imageBoxArea) {
		this.downloaders = new ArrayList<>();
		this.imageBoxArea = imageBoxArea;
		this.bar = InformationPanel.getBar();
		this.currentTask = InformationPanel.getCurrentTaskDes();
		this.downloadedImages = InformationPanel.getDownloadedImagesDes();
		this.settings = Settings.createSettings();
		prepare();
	}
	
	/**
	 * Prepares the {@link DownloadManager#bar} and sets the current minimum to
	 * the current downloaded megabytes and the maximum to the preferred maximum
	 * download of megabytes. Moreover, it will change the {@link DownloadManager#downloadedImages}
	 * to "Images: 0 / 0" if, no images was progressed before.
	 */
	private void prepare(){
		bar.setMinimum((int) ProgramMonitor.getDownloadedMegabyteAtRuntime());
		bar.setMaximum(settings.getProgramSettings().getMaxMegabyte());
		if ( ProgramMonitor.getAllProgressedFiles() == 0 )
			updateLabel(downloadedImages, "Images: 0 / 0");
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		DownloadManager.isRunning = true;
		ProgramMonitor.setIsDownloadersAreRunning(true);
		try {
			int size = settings.getProgramSettings().getThreads();
			updateLabel(currentTask, "Current task: Prepareing " + size + " downloaders.");
			Thread.sleep(200);
			prepareDownloaders();
			updateLabel(currentTask, "Current task: Starting " + size + " downloaders.");
			Thread.sleep(200);
			startDownloaders();
			System.out.println(settings.toStaticString());
			updateLabel(currentTask, "Current task: downloading...");
			while ( isRunning ); // wait until some one will change declaration "isRunning" 
			updateLabel(currentTask, "Current task: Waiting for all downloaders to stop. ( No new downloads )");
			stopDownloaders();
			while ( ProgramMonitor.getRegisteredDownloaders() != 0 ); // wait for all downloaders to stop
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void process(List<ImageData> chunks) {
		for ( ImageData data : chunks ) {
			imageBoxArea.addBox(new ImageBox(data));
			int barValue = (int) ProgramMonitor.getDownloadedMegabyteAtRuntime();
			int max = settings.getProgramSettings().getMaxMegabyte();
			bar.setValue(barValue);
			String barString = new DecimalFormat("####0.00").format(barValue) + " mb / "	+ max + " mb";
			bar.setString(barString);
			downloadedImages.setText("Images: " + ++totalNumber + " / " + ProgramMonitor.getAllDownloadedFiles());
			if ( barValue > max ) {
				DownloadManager.isRunning = false;
			}
		}
	}
	
	/**
	 * Updates a labels text and its tool tip text with the same string. 
	 * However, this is not true for {@link DownloadManager#downloadedImages}
	 * @param label	the label to change its data
	 * @param text	the new text for the label and its tool tip
	 */
	private void updateLabel(JLabel label, String text){
		label.setText(text);
		if ( label.hashCode() != downloadedImages.hashCode() )
			label.setToolTipText(text);
	}
	
	
	@Override
	protected void done() {
		ProgramMonitor.setIsDownloadersAreRunning(false);
		/**
		 * Since we assume, the program was running a long time and
		 * the user will restart searching for images. It would be nice
		 * to give the gc a hint, to perform its work. I know this
		 * is not good practice, however there is this nasty bufferedimage
		 * data behavior to flood the memory, so we should do this action.
		 */
		System.gc(); 		
		updateLabel(currentTask, "Current task: none");
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
