package imgurDiscoverer.frontent.componets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import imgurDiscoverer.backend.net.DownloadManager;
import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.frontent.frameextra.SettingsWindow;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Dimension buttonSize = new Dimension(30, 30);
	private GridBagConstraints constraints;
	private ImagePanel imagePanel;
	private JButton start;
	private JButton stop;
	private JButton settings;
	private JButton save;
	private List<ImageBox> imageBoxs;
	private ImageBoxArea imageBoxArea;
	private ControlPanel parent;
	private String newDest; 
	
	public ControlPanel(ImageBoxArea imageBoxArea) {
		this.imageBoxArea = imageBoxArea;
		this.imageBoxs = imageBoxArea.getBoxes();
		this.parent = this;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Utils.colorImgurLightGrey());
		setSize(new Dimension(1000, 400));
		setLayout(new GridBagLayout());
		//setDoubleBuffered(true);
		createLayout();
		initComponets();
		createActionListeners();
	}
	
	private void createLayout(){
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
	}

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
	
	private void copyFiles(){
		try {
			for ( ImageBox ib : imageBoxs ) {
				if ( ib.isSelected ) {
					Path dest = Paths.get(newDest + ib.getResourceName().getFileNameWithExtension(true));
					Path source = Paths.get(ib.getResourceName().getFileLocationAtDownloadTime());
					Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}