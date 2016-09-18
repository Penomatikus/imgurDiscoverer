package imgurDiscoverer.backend.settings;

import imgurDiscoverer.frontent.frameextra.SettingsWindow;

/**
 * Provides an object, holding information about the program 
 * behavior settings: 
 * <li> The amount of threads
 * <li> The index of {@link SettingsWindow}'s threadBox 
 * <li> If the found hashes should be written to file
 * <li> If the hashes with no result should be written to file
 * <li> If the previously found hashes should be added 
 * <li> If the previously found hashes with no result should be added
 * <li> If the program only check if hash is correct
 * <li> If the settings at all should be written to file for other sessions
 * 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class ProgramSettings {

	/**
	 * The amount of threads to use
	 */
	private int threads; 
	/**
	 * The index of {@link SettingsWindow}'s threadBox
	 */
	private int threadIndex;
	/**
	 * If the found hashes should be written to file
	 */
	private boolean saveFoundHashes;
	/**
	 * If the hashes with no result should be written to file
	 */
	private boolean saveNotFoundHashes;
	/**
	 * If the program only check if hash is correct
	 */
	private boolean allowDownload;
	/**
	 * The maximum amount of MB to download
	 */
	private int maxMegabyte;
	
	/**	
	 * Provides an object, holding information about the program 
	 * behavior settings: 
	 * <li> The amount of threads ( much more the index of {@link SettingsWindow}'s threadBox )
	 * <li> If the found hashes should be written to file
	 * <li> If the hashes with no result should be written to file
	 * <li> If the previously found hashes should be added 
	 * <li> If the previously found hashes with no result should be added
	 * <li> If the program only check if hash is correct
	 */
	public ProgramSettings() {
		threads = 4;
		threadIndex = 1;
		saveFoundHashes = true;
		saveNotFoundHashes = true;
		allowDownload = true;
		setMaxMegabyte(5000);
	}
	
	/**
	 * @return {@link ProgramSettings#threads}
	 */
	public int getThreads() {
		return threads;
	}
	
	/**
	 * @param threads {@link ProgramSettings#threads}
	 */
	public void setThreads(int threads){
		this.threads = threads;
		setThreadIndex();
	}

	/**
	 * Sets the index of {@link SettingsWindow}'s threadBox
	 */
	public void setThreadIndex() {
		switch(threads){
			case 2:   threadIndex = 0; break;
			case 4:   threadIndex = 1; break;
			case 8:   threadIndex = 2; break;
			case 16:  threadIndex = 3; break;
			case 32:  threadIndex = 4; break;
			case 64:  threadIndex = 5; break;
			case 128: threadIndex = 6; break;
			default: System.out.println("Jack... HOW?");

		}
	}
	
	public int getThreadBoxIndex(){
		return threadIndex;
	}
	/**
	 * @return the saveFoundHashes
	 */
	public boolean isSaveFoundHashes() {
		return saveFoundHashes;
	}
	/**
	 * @param saveFoundHashes the saveFoundHashes to set
	 */
	public void setSaveFoundHashes(boolean saveFoundHashes) {
		this.saveFoundHashes = saveFoundHashes;
	}
	/**
	 * @return the saveNotFoundHashes
	 */
	public boolean isSaveNotFoundHashes() {
		return saveNotFoundHashes;
	}
	/**
	 * @param saveNotFoundHashes the saveNotFoundHashes to set
	 */
	public void setSaveNotFoundHashes(boolean saveNotFoundHashes) {
		this.saveNotFoundHashes = saveNotFoundHashes;
	}
	/**
	 * @return the onlyDownload
	 */
	public boolean isDownloadAllowed() {
		return allowDownload;
	}
	/**
	 * @param onlyDownload the onlyDownload to set
	 */
	public void setIsDownloadAllowed(boolean allowDownload) {
		this.allowDownload = allowDownload;
	}

	public int getMaxMegabyte() {
		return maxMegabyte;
	}

	public void setMaxMegabyte(int maxMegabyte) {
		this.maxMegabyte = maxMegabyte;
	}
}
