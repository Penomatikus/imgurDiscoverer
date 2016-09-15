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
	 * Amount of threads
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
	 * If the previously found hashes should be added 
	 */
	private boolean addPreviouslyFound;
	/**
	 * If the previously found hashes with no result should be added
	 */
	private boolean addPreviouslyNotFound;
	/**
	 * If the program only check if hash is correct
	 */
	private boolean allowDownload;
	/**
	 * If the settings at all should be written to file for other sessions
	 */
	private boolean saveSettings;
	
	/**	
	 * Provides an object, holding information about the program 
	 * behavior settings: 
	 * <li> The amount of threads ( much more the index of {@link SettingsWindow}'s threadBox )
	 * <li> If the found hashes should be written to file
	 * <li> If the hashes with no result should be written to file
	 * <li> If the previously found hashes should be added 
	 * <li> If the previously found hashes with no result should be added
	 * <li> If the program only check if hash is correct
	 * <li> If the settings at all should be written to file for other sessions
	 */
	public ProgramSettings() {
		setThreads(16);
		threadIndex = 1;
		saveFoundHashes = true;
		saveNotFoundHashes = true;
		addPreviouslyFound = true;
		addPreviouslyNotFound = true;
		allowDownload = true;
		saveSettings = false;
	}
	
	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	/**
	 * @return the threadIndex
	 */
	public int getThreadIndex() {
		return threadIndex;
	}
	/**
	 * @param threadIndex the threadIndex to set
	 */
	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
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
	 * @return the addPreviouslyFound
	 */
	public boolean isAddPreviouslyFound() {
		return addPreviouslyFound;
	}
	/**
	 * @param addPreviouslyFound the addPreviouslyFound to set
	 */
	public void setAddPreviouslyFound(boolean addPreviouslyFound) {
		this.addPreviouslyFound = addPreviouslyFound;
	}
	/**
	 * @return the addPreviouslyNotFound
	 */
	public boolean isAddPreviouslyNotFound() {
		return addPreviouslyNotFound;
	}
	/**
	 * @param addPreviouslyNotFound the addPreviouslyNotFound to set
	 */
	public void setAddPreviouslyNotFound(boolean addPreviouslyNotFound) {
		this.addPreviouslyNotFound = addPreviouslyNotFound;
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
	/**
	 * @return the saveSettings
	 */
	public boolean isSaveSettings() {
		return saveSettings;
	}
	/**
	 * @param saveSettings the saveSettings to set
	 */
	public void setSaveSettings(boolean saveSettings) {
		this.saveSettings = saveSettings;
	}
	
	

}
