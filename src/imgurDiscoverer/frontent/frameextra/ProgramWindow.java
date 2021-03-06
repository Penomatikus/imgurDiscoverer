package imgurDiscoverer.frontent.frameextra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

import imgurDiscoverer.backend.monitoring.ProgramMonitor;
import imgurDiscoverer.backend.net.DownloadManager;
import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.frontent.componets.ControlPanel;
import imgurDiscoverer.frontent.componets.ImageBoxArea;
import imgurDiscoverer.frontent.componets.InformationPanel;

public class ProgramWindow extends JFrame implements Window {

	private static final long serialVersionUID = 1L;
	private ImageBoxArea imageBoxArea;
	private ProgramWindow self;
	private final WindowAdapter windowAdapter = new WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
			String message = "Do you realy want to quit imgurDiscoverer?";
			if ( ProgramMonitor.isDownloadersAreRunning() ) {
				message.concat("\nNote:\n"
					+ "All current download processes will be finished in the background.");
			}
			int i = JOptionPane.showConfirmDialog(self, message, "Quit?", JOptionPane.YES_NO_OPTION);
			if ( i == 0 ){
				DownloadManager.cancelDownloadProcess();
				dispose();	
			}
		};
	};
	
	public ProgramWindow(String title, int width, int height) {
		this.self = this;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(ResourceImage.programIcon));
		setTitle(title);
		setLayout(new BorderLayout());
		setBackground(new Color(20, 21, 24));
		setSize(width, height);
		double[] display = Utils.displaySize();
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension((int)display[0], (int)display[1]));
		int xLoc = (int)display[0] / 2 - (int)getSize().getWidth() / 2;
		int yLoc = (int)display[1] / 2 - (int)getSize().getHeight() / 2;
		setLocation(xLoc, yLoc);
		initComponents();
		addWindowListener(windowAdapter);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
	}
	
	@Override
	public void initComponents() {
		imageBoxArea = ImageBoxArea.createImageBoxArea(0, 0, getWidth(), getHeight());
		JScrollPane scrollPane = new JScrollPane(imageBoxArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setForeground(Utils.colorImgurLightGrey());
		scrollPane.setViewportBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		scrollPane.setDoubleBuffered(true);
		add(scrollPane, BorderLayout.CENTER);
		ImageBoxArea.addParent(scrollPane);
		
		add(new ControlPanel(imageBoxArea), BorderLayout.PAGE_START);
		
		add(InformationPanel.createInformationPanel(), BorderLayout.PAGE_END);
	}

	@Override
	public void launch() {
		new ProgramWindow("Imgur Discoverer", 1024, 768);
	}

}
