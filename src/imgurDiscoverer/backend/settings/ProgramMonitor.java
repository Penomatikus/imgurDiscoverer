package imgurDiscoverer.backend.settings;

import java.net.NoRouteToHostException;

import imgurDiscoverer.backend.logic.Singleton;
import imgurDiscoverer.backend.net.Downloader;

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
	 * Indicates the {@link Downloader}s to stop if true
	 */
	private static volatile short downloadSignal;
	/**
	 * Indicates all {@link NoRouteToHostException} encounters per session
	 */
	private static volatile int noRouteToHostExceptionCounter;
	
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
		noRouteToHostExceptionCounter = 0;
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
	 * Resets the downloaded megabyte at runtime and changes the value to 0.
	 * @return {@link ProgramMonitor#downloadedMegabyteAtRuntime}
	 */
	public static synchronized void resetDownloadedMegabyteAtRuntime(){
		ProgramMonitor.downloadedMegabyteAtRuntime = 0;
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
	
	/**
	 * Sends the {@link Downloader}s a signal to finish their work
	 * @param shouldStop
	 * @throws IllegalAccessException 
	 */
	public static void sendStopSignalToDownloaders(short newSignal) throws IllegalAccessException{
		if ( newSignal == Downloader.SIGNAL_RUN || newSignal == Downloader.SIGNAL_STOP )
			ProgramMonitor.downloadSignal = newSignal;
		else
			throw new IllegalAccessException("Use Downloader.SIGNAL_STOP or .SIGNAL_RUN ( " + newSignal + " used ).");
	}
	
	/**
	 * @return	{@link ProgramMonitor#stopSignal}
	 */
	public static synchronized short getDownloadSignal(){
		return ProgramMonitor.downloadSignal;
	}

	public static synchronized int getNoRouteToHostExceptionCounter() {
		return noRouteToHostExceptionCounter;
	}

	public static synchronized void setNoRouteToHostExceptionCounter(int noRouteToHostExceptionCounter) {
		ProgramMonitor.noRouteToHostExceptionCounter = noRouteToHostExceptionCounter;
	}

}
