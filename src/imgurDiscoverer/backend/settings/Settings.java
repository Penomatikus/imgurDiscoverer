package imgurDiscoverer.backend.settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import imgurDiscoverer.backend.logic.Singleton;

public class Settings implements Serializable, Singleton {

	private static final long serialVersionUID = 1L;
	private DirectorySettings directorySettings;
	private ProgramSettings programSettings;
	private static Settings self;
	
	private Settings() {
		directorySettings = new DirectorySettings();
		programSettings = new ProgramSettings();
	}
	
	public static Settings createSettings() {
		readSettingsFile();
		return ( self == null ) ? self = new Settings() : self;
	}

	/**
	 * @return the directorySettings
	 */
	public DirectorySettings getDirectorySettings() {
		return directorySettings;
	}

	/**
	 * @param directorySettings the directorySettings to set
	 */
	public void setDirectorySettings(DirectorySettings directorySettings) {
		this.directorySettings = directorySettings;
	}

	/**
	 * @return the programSettings
	 */
	public ProgramSettings getProgramSettings() {
		return programSettings;
	}

	/**
	 * @param programSettings the programSettings to set
	 */
	public void setProgramSettings(ProgramSettings programSettings) {
		this.programSettings = programSettings;
	}
	
	/**
	 * Writes the current {@link Settings#self} object to disk.
	 */
	public void createSettingsFile() {
		try {
			FileOutputStream fos = new FileOutputStream("settings");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(self);
			oos.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readSettingsFile(){
		Settings settings = null;
		try {
			FileInputStream fis = new FileInputStream("settings");
			ObjectInputStream ois = new ObjectInputStream(fis);
			settings = (Settings) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("[ Settings ] \".settings\" file not found.");
		}
		self = settings;
		createDirIfNotExist();
	}
	
	private static void createDirIfNotExist(){
		DirectorySettings directorySettings = self.getDirectorySettings();
		try {
			if ( !directorySettings.getPathForHashes().exists() ) 
				directorySettings.getPathForHashes().mkdirs();
			if ( !directorySettings.getPathForImages().exists() )
				directorySettings.getPathForImages().mkdirs();
				
		} catch (Exception e) {
			System.err.println("[ Settings ] Could not create one or borth of: \n" + 
					"\t - " + directorySettings.getPathForHashes().getAbsolutePath() + 
					"\n\t - " + directorySettings.getPathForImages().getAbsolutePath());
		}
		
			
	}
	
	public String toStaticString() {
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
