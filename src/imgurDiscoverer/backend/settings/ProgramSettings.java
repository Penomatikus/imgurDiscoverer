package imgurDiscoverer.backend.settings;

import java.io.Serializable;

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
public class ProgramSettings implements Serializable {

	private static final long serialVersionUID = 1L;
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
	private boolean notAllowDownload;
	/**
	 * The maximum amount of MB to download
	 */
	private int maxMegabyte;
	/**
	 * The index of {@link SettingsWindow}'s maxDownloads
	 */
	private int maxMegabyteIndex; 
	
	/**	
	 * Provides an object, holding information about the program 
	 * behavior settings: 
	 * <li> The amount of threads ( much more the index of {@link SettingsWindow}'s threadBox )
	 * <li> If the found hashes should be written to file
	 * <li> If the hashes with no result should be written to file
	 * <li> If the program only check if hash is correct
	 */
	public ProgramSettings() {
		threads = 8;
		threadIndex = 2;
		maxMegabyteIndex = 2;
		saveFoundHashes = true;
		saveNotFoundHashes = false;
		notAllowDownload = false;
		setMaxMegabyte(150);
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
			default: System.out.println("Jack... HOW? " + threads);

		}
	}
	
	public void setMaxDownloadsIndex(){
		switch (maxMegabyte) {
			case 50:	maxMegabyteIndex = 0; break;
			case 100:	maxMegabyteIndex = 1; break;
			case 150:	maxMegabyteIndex = 2; break;
			case 200:	maxMegabyteIndex = 3; break;
			case 250:	maxMegabyteIndex = 4; break;
			case 300:	maxMegabyteIndex = 5; break;
			case 350:	maxMegabyteIndex = 6; break;
			case 400:	maxMegabyteIndex = 7; break;
			case 450:	maxMegabyteIndex = 8; break;
			case 500:	maxMegabyteIndex = 9; break;
			case 550:	maxMegabyteIndex = 10; break; 
			default: System.out.println("Jack... HOW? " + threads);
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
		return notAllowDownload;
	}
	/**
	 * @param onlyDownload the onlyDownload to set
	 */
	public void setIsDownloadAllowed(boolean allowDownload) {
		this.notAllowDownload = allowDownload;
	}

	public int getMaxMegabyte() {
		return maxMegabyte;
	}

	public void setMaxMegabyte(int maxMegabyte) {
		this.maxMegabyte = maxMegabyte;
	}
	
	public int getMaxMegabyteIndex(){
		return maxMegabyteIndex;
	}
}
