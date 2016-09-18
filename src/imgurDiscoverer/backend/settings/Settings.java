package imgurDiscoverer.backend.settings;

import java.io.Serializable;

import imgurDiscoverer.backend.logic.Singleton;

public class Settings implements Serializable, Singleton {

	private static final long serialVersionUID = 1L;
	private static DirectorySettings directorySettings;
	private static ProgramSettings programSettings;
	private static Settings self;
	
	private Settings() {
		directorySettings = new DirectorySettings();
		programSettings = new ProgramSettings();
	}
	
	public static Settings createSettings(){
		return ( self == null ) ? self = new Settings() : self;
	}

	/**
	 * @return the directorySettings
	 */
	public static DirectorySettings getDirectorySettings() {
		return directorySettings;
	}

	/**
	 * @param directorySettings the directorySettings to set
	 */
	public static void setDirectorySettings(DirectorySettings directorySettings) {
		Settings.directorySettings = directorySettings;
	}

	/**
	 * @return the programSettings
	 */
	public static ProgramSettings getProgramSettings() {
		return Settings.programSettings;
	}

	/**
	 * @param programSettings the programSettings to set
	 */
	public static void setProgramSettings(ProgramSettings programSettings) {
		Settings.programSettings = programSettings;
	}
	
	public static String toStaticString() {
		return 	"\nSettings set to: \n" +
				"----------------- \n" +
				"Current image path: " + getDirectorySettings().getPathForImages() + "\n" +
				"Current hash path: " + getDirectorySettings().getPathForHashes() + "\n"  +
				"-----------------\n" +
				"Threads in use: " + getProgramSettings().getThreads() + "\n" +
				"Maximum MB to download: " +  getProgramSettings().getMaxMegabyte() + "\n" +
				"Save hashes of found images: " + getProgramSettings().isSaveFoundHashes() + "\n" +
				"Save hashes of not found images; " + getProgramSettings().isSaveNotFoundHashes() + "\n" +
				"Only check if exist: " + getProgramSettings().isDownloadAllowed() + "\n\n";
			
	}

}
