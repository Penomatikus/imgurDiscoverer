package imgurDiscoverer.backend.settings;

import imgurDiscoverer.backend.logic.Singleton;

/**
 * Holds and monitors program information like, 
 * if the downloaders are currently running.
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class ProgramMonitor implements Singleton {
	
	/**
	 * Its self
	 */
	private static ProgramMonitor self;
	/**
	 * Indicates if the the downloaders are running
	 */
	private static boolean downloadersAreRunning;
	/**
	 * Indicates the downloaded MB at runtime;
	 */
	private static volatile double downloadedMegabyteAtRuntime;
	/**
	 * All downloaded files 
	 */
	private static volatile long allDownloadedFiles;
	/**
	 * All progressed files
	 */
	private static long allProgressedFiles;
	/**
	 * All registered downloaders
	 */
	private static int registeredDownloaders;
	
	/**
	 * Holds and monitors program information like, 
	 * if the downloaders are currently running.
	 */
	private ProgramMonitor() {
		downloadersAreRunning = false;
		downloadedMegabyteAtRuntime = 0;
		allDownloadedFiles = 0l;
		allProgressedFiles = 0l;
		registeredDownloaders = 0;
	}

	/**
	 * Creates a new {@link ProgramMonitor} or returns the current
	 * instance.
	 * @return a new {@link ProgramMonitor} or returns the current
	 * instance.
	 */
	public static synchronized ProgramMonitor createProgramMonitor(){
		return ( self == null ) ? self = new ProgramMonitor() : self;
	}
	
	/**
	 * @return {@link ProgramMonitor#downloadersAreRunning}
	 */
	public static boolean isDownloadersAreRunning(){
		return downloadersAreRunning;
	}
	
	/**
	 * Sets {@link ProgramMonitor#downloadersAreRunning}
	 */
	public static void setIsDownloadersAreRunning(boolean isRunning){
		ProgramMonitor.downloadersAreRunning = isRunning;
	}
	
	/**
	 * Adds a new value to the current downloaded megabytes
	 * @param addition new value added to the current downloaded megabytes
	 */
	public static synchronized void addDownloadedMegabyteAtRuntime(double addition){
		ProgramMonitor.downloadedMegabyteAtRuntime += addition;
	}
	
	/**
	 * @return {@link ProgramMonitor#downloadedMegabyteAtRuntime}
	 */
	public static synchronized double getDownloadedMegabyteAtRuntime(){
		return ProgramMonitor.downloadedMegabyteAtRuntime;
	}

	/**
	 * @return the allFiles
	 */
	public static synchronized long getAllDownloadedFiles() {
		return allDownloadedFiles;
	}

	/**
	 * @param allFiles the allFiles to set
	 */
	public static synchronized void setAllDownloadedFiles(long allFiles) {
		ProgramMonitor.allDownloadedFiles = allFiles;
	}

	/**
	 * @return the registeredDownloaders
	 */
	public static synchronized int getRegisteredDownloaders() {
		return registeredDownloaders;
	}

	/**
	 * @param registeredDownloaders the registeredDownloaders to set
	 */
	public static synchronized void setRegisteredDownloaders(int registeredDownloaders) {
		ProgramMonitor.registeredDownloaders += registeredDownloaders;
	}
	
	/**
	 * @return the allProgressedFiles
	 */
	public static long getAllProgressedFiles() {
		return allProgressedFiles;
	}

	/**
	 * @param allProgressedFiles the allProgressedFiles to set
	 */
	public static void setAllProgressedFiles(long allProgressedFiles) {
		ProgramMonitor.allProgressedFiles = allProgressedFiles;
	}

}
