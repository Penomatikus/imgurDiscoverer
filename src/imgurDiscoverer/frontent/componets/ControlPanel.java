package imgurDiscoverer.frontent.componets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import imgurDiscoverer.backend.net.DownloadManager;
import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.frontent.frameextra.SettingsWindow;

/**
 * Provides an object for generating a panel to control the program with user
 * interactions. <br>
 * <li> Start a download session
 * <li> Stop a running download session
 * <li> Save selected images to another location
 * <li> Open preferences
 * <br><br>
 * The panel its self have a fixed size of 1000px x 400px with a padding of 10px
 * at each side. 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * Holds the button size
	 */
	private final Dimension buttonSize = new Dimension(30, 30);
	/**
	 * Used to describe the layout
	 */
	private GridBagConstraints constraints;
	/**
	 * Loads {@link ResourceImage#logo}
	 */
	private ImagePanel imagePanel;
	/**
	 * Button to start a search
	 */
	private JButton start;
	/**
	 * Button to stop a search
	 */
	private JButton stop;
	/**
	 * Button to open the settings window
	 */
	private JButton settings;
	/**
	 * Button to save selected images to another place
	 */
	private JButton save;
	/**
	 * References of selected images
	 */
	private List<ImageBox> imageBoxs;
	/**
	 * Needed as reference
	 */
	private ImageBoxArea imageBoxArea;
	/**
	 * The {@link ControlPanel} its self as parent reference for listeners 
	 */
	private ControlPanel parent;
	/**
	 * Holds the new copy location for images
	 */
	private String newDest; 
	
	/**
	 * Provides an object for generating a panel to control the program with user
	 * interactions. <br>
	 * <li> Start a download session
	 * <li> Stop a running download session
	 * <li> Save selected images to another location
	 * <li> Open preferences
	 * <br><br>
	 * The panel its self have a fixed size of 1000px x 400px with a padding of 10px
	 * at each side. 
	 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
	 *
	 */
	public ControlPanel(ImageBoxArea imageBoxArea) {
		this.imageBoxArea = imageBoxArea;
		this.imageBoxs = imageBoxArea.getBoxes();
		this.parent = this;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Utils.colorImgurLightGrey());
		setSize(new Dimension(1000, 400));
		setLayout(new GridBagLayout());
		createLayout();
		initComponets();
		createActionListeners();
	}
	
	/**
	 * Initializes the constrains
	 */
	private void createLayout(){
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
	}

	/**
	 * Generates the look
	 */
	private void initComponets(){
		imagePanel = new ImagePanel(ResourceImage.logo, 0, 0);
		add(imagePanel, constraints);
	
		JPanel buttons = new JPanel(new GridBagLayout());
		buttons.setBackground(new Color(52, 55, 60));
		constraints.gridx++;
		add(buttons, constraints);
		
		start = new JButton(new ImageIcon(ResourceImage.start));
		start.setPreferredSize(buttonSize);
		constraints.gridx++;
		constraints.insets = new Insets(10, 15, 5, 5);
		buttons.add(start, constraints);
		
		constraints.insets = new Insets(10, 5, 5, 5);
		stop = new JButton(new ImageIcon(ResourceImage.stop));
		stop.setPreferredSize(buttonSize);
		constraints.gridx++;
		buttons.add(stop, constraints);
		
		constraints.insets = new Insets(5, 15, 5, 5);
		save = new JButton(new ImageIcon(ResourceImage.save));
		save.setPreferredSize(buttonSize);
		constraints.gridx = 2; 
		constraints.gridy++;
		buttons.add(save, constraints);
		
		constraints.insets = new Insets(5, 5, 5, 5);
		settings = new JButton(new ImageIcon(ResourceImage.settings));
		settings.setPreferredSize(buttonSize);
		constraints.gridx++;
		buttons.add(settings, constraints);
	}
	
	/**
	 * Adds action listeners to the buttons
	 */
	private void createActionListeners(){
		start.addActionListener((e) -> {
			if ( !ProgramMonitor.isDownloadersAreRunning() )
				new DownloadManager(imageBoxArea).execute();
			else 
				JOptionPane.showMessageDialog(parent,
						"A download process is already running.");
		});
		
		stop.addActionListener((e) -> {
			if ( ProgramMonitor.isDownloadersAreRunning() ) 
				DownloadManager.cancelDownloadProcess();
			else 
				JOptionPane.showMessageDialog(parent,
						"No download progress is running");
		});
		
		save.addActionListener((e) -> {
			if ( imageBoxs.size() == 0 )
				JOptionPane.showMessageDialog(parent,
						"You have to select one or more images first.");
			else {
				newDest = askForNewPath();
				copyFiles();
			}	
		});
		
		settings.addActionListener((e) -> {
			SwingUtilities.invokeLater(() -> {
				new SettingsWindow();
			});
		});
	}
	
	/**
	 * Used to get the new user preferred path to copy the selected images 
	 * to
	 * @return the new user preferred path to copy the selected images 
	 */
	private String askForNewPath(){
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setAcceptAllFileFilterUsed(false);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setDialogTitle("Where would you like to copy to?");
	    File newPath = null;
	    if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
	    	newPath = chooser.getSelectedFile();
	    }
	    return newPath.getAbsolutePath();		
	}
	
	/**
	 * Copys the selected images to the new destination and shows the copy
	 * state in a new {@link CopyWindow}, which will be disposed after the 
	 * progress is finished.
	 */
	private void copyFiles(){
		CopyWindow copyWindow = new CopyWindow(imageBoxs.size());
		try {
			int i = 0;
			for ( ImageBox ib : imageBoxs ) {
				copyWindow.updateBar(i++);
				if ( ib.isSelected ) {
					Path dest = Paths.get(newDest + ib.getResourceName().getFileNameWithExtension(true));
					Path source = Paths.get(ib.getResourceName().getFileLocationAtDownloadTime());
					Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		copyWindow.dispose();
	}
	
	/**
	 * Provides an object for showing a copy dialog.
	 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
	 *
	 */
	private class CopyWindow extends JFrame {

		private static final long serialVersionUID = 1L;
		/**
		 * The {@link JProgressBar} to show the current progress
		 */
		private JProgressBar bar;
	
		/**
		 * Provides an object for showing a copy dialog.
		 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
		 *
		 */
		public CopyWindow(int maximum) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(ResourceImage.programIcon));
			setSize(200, 60);
			double[] display = Utils.displaySize();
			int xLoc = (int)display[0] / 2 - (int)getSize().getWidth() / 2;
			int yLoc = (int)display[1] / 2 - (int)getSize().getHeight() / 2;
			setLocation(xLoc, yLoc);
			setVisible(true);
			
			bar = new JProgressBar(0, maximum);
			bar.setBounds(10, 10, 100, 30);
			add(bar);
		}
		
		/**
		 * Updates the bar value
		 * @param value new bar value
		 */
		public void updateBar(int value){
			bar.setValue(value);
			bar.setStringPainted(true);
			bar.setString("Copy files");
		}
		
	}
	
}