package imgurDiscoverer;

import javax.swing.SwingUtilities;

import imgurDiscoverer.backend.settings.DirectorySettings;
import imgurDiscoverer.frontent.frameextra.ProgramWindow;

public class ImgurDiscoverer {

	public static void main(String[] args) {
		
		DirectorySettings s = new DirectorySettings();
		System.out.println(s.getPathForHashes() + "\n" + s.getPathForImages() + "\n" + s.getPathForSettings());
		
		SwingUtilities.invokeLater(() -> {
			new ProgramWindow("Imgur Discoverer", 1024, 768);
		});
	}

}
