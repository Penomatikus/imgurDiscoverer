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
	 * Holds and monitors program information like, 
	 * if the downloaders are currently running.
	 */
	private ProgramMonitor() {
		downloadersAreRunning = false;
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
	
	

}
