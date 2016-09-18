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
