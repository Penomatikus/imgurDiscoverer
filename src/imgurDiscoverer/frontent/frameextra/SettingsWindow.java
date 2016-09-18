package imgurDiscoverer.frontent.frameextra;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.frontent.componets.ImagePanel;
import imgurDiscoverer.frontent.componets.IntelligentTextfield;

public class SettingsWindow extends JFrame implements Window {

	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> threadBox;
	private JLabel threadBoxDescription;
	private JCheckBox saveFound;
	private JCheckBox saveNotFound;
	private JCheckBox onlyCheckNotDownload;
	private IntelligentTextfield maxDownload;
	private JButton ok;
	private JButton abort;
	private JButton previous;
	private JButton defaults;
	private JPanel container;
	private SettingsWindow parent;
	
	public SettingsWindow() {
		Settings.createSettings();
		parent = this;
		setSize(400, 500);
		double[] display = Utils.displaySize();
		int xLoc = (int)display[0] / 2 - (int)getSize().getWidth() / 2;
		int yLoc = (int)display[1] / 2 - (int)getSize().getHeight() / 2;
		setLocation(xLoc, yLoc);
		setTitle("Settings");
		initComponents();
		addActionListeners();
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void initComponents() {
		Font font = new Font("SansSerif", Font.BOLD, 15);
		
		container = new JPanel();
		container.setLayout(null);
		container.setBounds(0, 0, 400, 500);
		container.setBackground(new Color(52, 55, 60 ));
		add(container);
		
		container.add(new ImagePanel(ResourceImage.settingsHeader, 80, 0));
				
		threadBoxDescription = new JLabel("Choose the amount of threads");
		threadBoxDescription.setBounds(10, 100, 250, 30);
		threadBoxDescription.setFont(font);
		threadBoxDescription.setHorizontalAlignment(SwingConstants.CENTER);
		threadBoxDescription.setVerticalAlignment(SwingConstants.CENTER);
		threadBoxDescription.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		threadBoxDescription.setVisible(true);
		threadBoxDescription.setForeground(Color.white);
		container.add(threadBoxDescription);
		
		threadBox = new JComboBox<Integer>(new Integer[]{ 2, 4, 8, 16, 32, 68, 128});
		threadBox.setBounds(280, 103, 100, 25);
		threadBox.setBackground(new Color(52, 55, 60 ));
		threadBox.setSelectedIndex(Settings.getProgramSettings().getThreadBoxIndex());
		container.add(threadBox);
		
		JLabel maxDownloadDescription = new JLabel("Maximum download size in MB");
		maxDownloadDescription.setBounds(10, 140, 250, 30);
		maxDownloadDescription.setFont(font);
		maxDownloadDescription.setHorizontalAlignment(SwingConstants.CENTER);
		maxDownloadDescription.setVerticalAlignment(SwingConstants.CENTER);
		maxDownloadDescription.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		maxDownloadDescription.setVisible(true);
		maxDownloadDescription.setForeground(Color.white);
		container.add(maxDownloadDescription);
		
		maxDownload = new IntelligentTextfield(280, 143, 100, 25);
		maxDownload.setText(Settings.getProgramSettings().getMaxMegabyte() + "");
		container.add(maxDownload);
		
		saveFound = new JCheckBox("Save hashes of the found images");
		saveFound.setToolTipText("Saves the names of the found images to a file");
		saveFound.setSelected(Settings.getProgramSettings().isSaveFoundHashes());
		saveFound.setBounds(5, 170, 375, 30);
		saveFound.setFont(font);
		saveFound.setForeground(Color.white);
		container.add(saveFound);
		
		saveNotFound = new JCheckBox("Save hashes which result in no image");
		saveNotFound.setToolTipText("Saves the hashes of the found images to a file");
		saveNotFound.setSelected(Settings.getProgramSettings().isSaveNotFoundHashes());
		saveNotFound.setBounds(5, 200, 375, 30);
		saveNotFound.setFont(font);
		saveNotFound.setForeground(Color.white);
		container.add(saveNotFound); 
		
		onlyCheckNotDownload = new JCheckBox("Only check if image exists.");
		onlyCheckNotDownload.setToolTipText("Does not download found images, but tells you if they exists.");
		onlyCheckNotDownload.setSelected(Settings.getProgramSettings().isDownloadAllowed());
		onlyCheckNotDownload.setBounds(5, 230, 375, 30);
		onlyCheckNotDownload.setFont(font);
		onlyCheckNotDownload.setForeground(Color.white);
		container.add(onlyCheckNotDownload);
		
		ok = new JButton("OK");
		ok.setToolTipText("Use the current settings and close the window");
		ok.setBounds(5, 400, 190, 30);
		ok.setFont(font);
		container.add(ok);
		
		abort = new JButton("Abort");
		abort.setToolTipText("Closes the window and dont use the new settings");
		abort.setBounds(200, 400, 190, 30);
		abort.setFont(font);
		container.add(abort);
		
		defaults = new JButton("Restore defaults");
		defaults.setToolTipText("Restores the settings to the default imgur discoverer settings");
		defaults.setBounds(5, 440, 190, 30);
		defaults.setFont(font);
		container.add(defaults);
		
		previous = new JButton("Previous settings");
		previous.setToolTipText("Changes the settings to the previously used ones");
		previous.setBounds(200, 440, 190, 30);
		previous.setFont(font);
		container.add(previous);
			
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub	
	}
	
	private void addActionListeners(){
		ok.addActionListener( (e) -> {
			if ( !ProgramMonitor.isDownloadersAreRunning() ) {
				Settings.getProgramSettings().setThreads((int)threadBox.getSelectedItem());
				Settings.getProgramSettings().setSaveFoundHashes(saveFound.isSelected());
				Settings.getProgramSettings().setSaveNotFoundHashes(saveNotFound.isSelected());
				Settings.getProgramSettings().setIsDownloadAllowed(onlyCheckNotDownload.isSelected());
				Settings.getProgramSettings().setMaxMegabyte(Integer.parseInt(maxDownload.getText()));
				System.out.println(Settings.toStaticString());
				dispose();
			} else {
				JOptionPane.showMessageDialog(parent, 
						"Can't apply settings while download is in process.");
			}
		});
		
		abort.addActionListener( (e) -> {
			dispose();
		});
		
		defaults.addActionListener( (e) -> {
			System.out.println("defaults");
		});
		
		previous.addActionListener( (e) -> {
			System.out.println("previous");
		});	
	}
	
}
