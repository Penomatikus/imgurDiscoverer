package imgurDiscoverer.backend.view;

import javax.swing.SwingUtilities;

import imgurDiscoverer.frontent.frameextra.Window;

public class Starter {

	public static void start(Window window){
		SwingUtilities.invokeLater(() -> {
			window.launch();
		});
	}
	
	
}
