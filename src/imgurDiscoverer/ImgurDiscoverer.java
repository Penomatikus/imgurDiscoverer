package imgurDiscoverer;

import javax.swing.SwingUtilities;

import imgurDiscoverer.frontent.frameextra.ProgramWindow;

public class ImgurDiscoverer {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
		new ProgramWindow("Imgur Discoverer", 1024, 768);
		});
	}

}
