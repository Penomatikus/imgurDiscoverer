package imgurDiscoverer.backend.settings;

import java.io.File;

/**
 * Provides an object, holding all information about 
 * directories to save certain files to: 
 * <li> path for the downloaded images
 * <li> path for the calculated hashes ( image names ) 
 * <li> path for the settings its self <br><br>
 * The default directories will be created in the temp
 * directory of the operating system under ../imgurDiscoverer/..
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class DirectorySettings {
	
	/**
	 * The path to all downloaded images
	 */
	private static File pathForImages = dir("pathForImages"); 
	/**
	 * The path to the hash files
	 */
	private static File pathForHashes = dir("pathForHashes");
	/**
	 * The path to the the settings-file
	 */
	private static File pathForSettings = dir("pathForSettings");
	
	/**
	 * Creates the default directories for the {@link DirectorySettings}.   
	 * @param subDirectory
	 * @return
	 */
	private static File dir(String subDirectory) {
		String b_slash = File.separator;
		String tmpDir = System.getProperty("java.io.tmpdir");
		File dir = null;
		try {
			dir = new File( tmpDir + b_slash + "imgurDiscoverer" +
						b_slash + subDirectory);
			if ( !dir.exists() )
				dir.mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dir;
	}

	/**
	 * @return the pathForImages
	 */
	public File getPathForImages() {
		return pathForImages;
	}
	/**
	 * @param pathForImages the pathForImages to set
	 */
	public void setPathForImages(File pathForImages) {
		DirectorySettings.pathForImages = pathForImages;
	}
	/**
	 * @return the pathForHashes
	 */
	public File getPathForHashes() {
		return pathForHashes;
	}
	/**
	 * @param pathForHashes the pathForHashes to set
	 */
	public void setPathForHashes(File pathForHashes) {
		DirectorySettings.pathForHashes = pathForHashes;
	}
	/**
	 * @return the pathForSettings
	 */
	public File getPathForSettings() {
		return pathForSettings;
	}
	/**
	 * @param pathForSettings the pathForSettings to set
	 */
	public void setPathForSettings(File pathForSettings) {
		DirectorySettings.pathForSettings = pathForSettings;
	}
	
	

}
