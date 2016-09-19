package imgurDiscoverer.backend.resources;

import java.net.URL;

/**
 * Interface providing program resources 
 * @author stefan
 *
 */
public interface ResourceImage {
	
	public URL logo = ResourceImage.class.getResource("/logo.png");
	public URL settingsHeader = ResourceImage.class.getResource("/settingsHead.png");
	public URL selected = ResourceImage.class.getResource("/selected.png");
	public URL programIcon = ResourceImage.class.getResource("/imgurdiscoverer.png");

	
	/***** Thanks to: https://www.iconfinder.com/iconsets/small-n-flat**/
	public URL start = ResourceImage.class.getResource("/start.png");
	public URL stop = ResourceImage.class.getResource("/stop.png");
	public URL settings = ResourceImage.class.getResource("/settings.png");
	public URL load = ResourceImage.class.getResource("/load.png");
	public URL save = ResourceImage.class.getResource("/save.png");
	public URL dic = ResourceImage.class.getResource("/dic.png");
	/******************************************************************/

	
	
}
