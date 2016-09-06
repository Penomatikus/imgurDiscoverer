package imgurDiscoverer;

import javax.swing.SwingUtilities;

import imgurDiscoverer.frontent.frameextra.ProgramWindow;
import imgurDiscoverer.frontent.frameextra.SettingsWindow;

public class ImgurDiscoverer {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			//new SettingsWindow();
			new ProgramWindow("Imgur Discoverer", 1024, 768);
		});
	}

}
