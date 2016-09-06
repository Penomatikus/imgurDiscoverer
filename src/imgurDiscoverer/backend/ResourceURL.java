package imgurDiscoverer.backend;

import java.net.URL;

/**
 * Interface providing program resources 
 * @author stefan
 *
 */
public interface ResourceURL {
	
	public URL logo = ResourceURL.class.getResource("/logo.png");
	public URL settingsHeader = ResourceURL.class.getResource("/settingsHead.png");
	public URL selected = ResourceURL.class.getResource("/selected.png");

	
	/***** Thanks to: https://www.iconfinder.com/iconsets/small-n-flat**/
	public URL start = ResourceURL.class.getResource("/start.png");
	public URL stop = ResourceURL.class.getResource("/stop.png");
	public URL settings = ResourceURL.class.getResource("/settings.png");
	public URL load = ResourceURL.class.getResource("/load.png");
	public URL save = ResourceURL.class.getResource("/save.png");
	/******************************************************************/

	
	
}
