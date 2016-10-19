/*
 *************************************************************
 *															**
 * IMPORTANT: Run this program with the following JVM args: **
 * -Xms128m 												**
 * -Xmx325m 												**
 * -XX:GCTimeRatio=19 										**
 * -XX:+UseParallelOldGC 									**
 * -XX:+DisableExplicitGC 									**
 * -XX:MaxGCPauseMillis=150 								**
 * -XX:InitiatingHeapOccupancyPercent=0						**
 * 															**
 * Otherwise you may run in to an extended use of you heap, **
 * since this program handles a lot of image data.			**
 * 															**
 * NOTE: This program may use the VRAM as well.				**
 *															**
 *************************************************************
 */

package imgurDiscoverer;

import javax.swing.SwingUtilities;

import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.frontent.frameextra.ProgramWindow;

public class ImgurDiscoverer {

	public static void main(String[] args) {
		
		Utils.prepareStartUp();		
		SwingUtilities.invokeLater(() -> {
			new ProgramWindow("Imgur Discoverer", 1024, 768);
		});
	}

}
