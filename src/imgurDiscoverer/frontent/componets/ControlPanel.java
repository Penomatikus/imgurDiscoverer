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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import imgurDiscoverer.backend.ResourceURL;
import imgurDiscoverer.backend.Utils;
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
	
	public ControlPanel(List<ImageBox> imageBoxs) {
		this.imageBoxs = imageBoxs;
		
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
		imagePanel = new ImagePanel(ResourceURL.logo, 0, 0);
		add(imagePanel, constraints);
	
		JPanel buttons = new JPanel(new GridBagLayout());
		buttons.setBackground(new Color(52, 55, 60));
		constraints.gridx++;
		add(buttons, constraints);
		
		start = new JButton(new ImageIcon(ResourceURL.start));
		start.setPreferredSize(buttonSize);
		constraints.gridx++;
		constraints.insets = new Insets(10, 15, 5, 5);
		buttons.add(start, constraints);
		
		constraints.insets = new Insets(10, 5, 5, 5);
		stop = new JButton(new ImageIcon(ResourceURL.stop));
		stop.setPreferredSize(buttonSize);
		constraints.gridx++;
		buttons.add(stop, constraints);
		
		settings = new JButton(new ImageIcon(ResourceURL.settings));
		settings.setPreferredSize(buttonSize);
		constraints.gridx++;
		buttons.add(settings, constraints);
		
		constraints.insets = new Insets(5, 15, 5, 5);
		save = new JButton(new ImageIcon(ResourceURL.save));
		save.setPreferredSize(buttonSize);
		constraints.gridx = 2; 
		constraints.gridy++;
		buttons.add(save, constraints);
		
		constraints.insets = new Insets(5, 5, 5, 5);
		load = new JButton(new ImageIcon(ResourceURL.load));
		load.setPreferredSize(buttonSize);
		constraints.gridx++;
		buttons.add(load, constraints);
	}
	
	private void createActionListeners(){
		start.addActionListener((e) -> {
			
		});
		
		stop.addActionListener((e) -> {
			
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