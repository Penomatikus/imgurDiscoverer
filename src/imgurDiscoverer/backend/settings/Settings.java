package imgurDiscoverer.backend.settings;

import java.io.Serializable;

public class Settings implements Serializable {

	private static final long serialVersionUID = 1L;
	private DirectorySettings directorySettings;
	private ProgramSettings programSettings;
	
	public Settings() {
		directorySettings = new DirectorySettings();
		programSettings = new ProgramSettings();
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
	
	

}
