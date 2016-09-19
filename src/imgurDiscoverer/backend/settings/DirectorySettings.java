package imgurDiscoverer.backend.settings;

import java.io.File;
import java.io.Serializable;

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
public class DirectorySettings implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_PATH_HASHES = "pathForHashes";
	public static final String DEFAULT_PATH_IMAGES = "pathForImages";
	public static final String IMAGE_SET_NAME 	= "imagesettings";
	public static final String HASH_SET_NAME 	= "hashsettings";
	
	
	/**
	 * The path to all downloaded images
	 */
	private File pathForImages = tmpdir(DEFAULT_PATH_IMAGES); 
	/**
	 * The path to the hash files
	 */
	private File pathForHashes = tmpdir(DEFAULT_PATH_HASHES);
	
	public void overwriteSettings(DirectorySettings s){
		setPathForHashes(s.pathForHashes);
		setPathForImages(s.pathForImages);
	}
	
	/**
	 * Creates the default directories for the {@link DirectorySettings}.   
	 * @param subDirectory
	 * @return
	 */
	public File tmpdir(String subDirectory) {
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
		this.pathForImages = pathForImages;
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
		this.pathForHashes = pathForHashes;
	}

	
	

}
