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
	private static double downloadedMegabyteAtRuntime;
	
	/**
	 * Holds and monitors program information like, 
	 * if the downloaders are currently running.
	 */
	private ProgramMonitor() {
		downloadersAreRunning = false;
		downloadedMegabyteAtRuntime = 0l;
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
	public static void addDownloadedMegabyteAtRuntime(double addition){
		ProgramMonitor.downloadedMegabyteAtRuntime += addition;
	}
	
	/**
	 * @return {@link ProgramMonitor#downloadedMegabyteAtRuntime}
	 */
	public static double getDownloadedMegabyteAtRuntime(){
		return ProgramMonitor.downloadedMegabyteAtRuntime;
	}
	
	

}
