package imgurDiscoverer.frontent.componets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	private JButton load;
	private JButton save;
	private JButton settings;
	private List<ImageBox> imageBoxs;
	private ImageBoxArea imageBoxArea;
	private ControlPanel parent;
	
	public ControlPanel(ImageBoxArea imageBoxArea) {
		this.imageBoxArea = imageBoxArea;
		this.imageBoxs = imageBoxArea.getBoxes();
		this.parent = this;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Utils.colorImgurLightGrey());
		setSize(new Dimension(1000, 400));
		setLayout(new GridBagLayout());
		setDoubleBuffered(true);
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
		
		settings = new JButton(new ImageIcon(ResourceImage.settings));
		settings.setPreferredSize(buttonSize);
		constraints.gridx++;
		buttons.add(settings, constraints);
		
		constraints.insets = new Insets(5, 15, 5, 5);
		save = new JButton(new ImageIcon(ResourceImage.save));
		save.setPreferredSize(buttonSize);
		constraints.gridx = 2; 
		constraints.gridy++;
		buttons.add(save, constraints);
		
		constraints.insets = new Insets(5, 5, 5, 5);
		load = new JButton(new ImageIcon(ResourceImage.load));
		load.setPreferredSize(buttonSize);
		constraints.gridx++;
		buttons.add(load, constraints);
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
			DownloadManager.cancelDownloadProcess();
		});
		
		save.addActionListener((e) -> {
			for ( ImageBox ib : imageBoxs )
				if ( ib.isSelected ) 
					System.out.println(ib.getResourceName());
	
		});
		
		load.addActionListener((e) -> {
	
		});
		
		settings.addActionListener((e) -> {
			SwingUtilities.invokeLater(() -> {
				new SettingsWindow();
			});
		});

	}
}