package imgurDiscoverer.frontent.frameextra;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.frontent.componets.ImagePanel;
import imgurDiscoverer.frontent.componets.InformationPanel;

public class SettingsWindow extends JFrame implements Window {

	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> threadBox;
	private JComboBox<Integer> maxDownloads;
	private JLabel threadBoxDescription;
	private JCheckBox saveFound;
	private JCheckBox saveNotFound;
	private JLabel saveImagePath;
	private JLabel saveHashDes;
	private JLabel saveImageDes;
	private JCheckBox onlyCheckNotDownload;
	private JButton ok;
	private JButton abort;
	private JButton defaults;
	private JButton changeDic1;
	private JButton changeDic2;
	private JPanel container;
	private SettingsWindow parent;
	private Settings settings;
	
	private final ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser(); 
		    chooser.setAcceptAllFileFilterUsed(false);
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setDialogTitle("Choose a new destination");

		    if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			    if ( e.getSource().equals(changeDic1) ) {
				    settings.getDirectorySettings().setPathForHashes(chooser.getSelectedFile());
				    saveHashDes.setText(settings.getDirectorySettings().getPathForHashes().
				    		getAbsolutePath());
			    }
				else if ( e.getSource().equals(changeDic2) ) {
				    settings.getDirectorySettings().setPathForImages(chooser.getSelectedFile());
				    saveImageDes.setText(settings.getDirectorySettings().getPathForImages().
				    		getAbsolutePath());
			    }
			}
			
		}
	};
	
	public SettingsWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ResourceImage.programIcon));
		settings = Settings.createSettings();
		parent = this;
		setSize(400, 540);
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
		Font font = Utils.defaultFontBold(15);
		Font font2 = Utils.defaultFontPlain(15);
		
		container = new JPanel();
		container.setLayout(null);
		container.setBounds(0, 0, 400, 580);
		container.setBackground(Utils.colorImgurDarkGrey());
		add(container);
		
		JPanel header = new JPanel();
		header.setSize(new Dimension(400, 80));
		header.setBackground(Utils.colorImgurLightGrey());
		header.add(new ImagePanel(ResourceImage.settingsHeader, 80, 0));
		container.add(header);
		
		Border border = BorderFactory.createTitledBorder(null, "Advanced", TitledBorder.LEFT, TitledBorder.TOP, font2, Utils.colorImgurGreen());
		JPanel advanced = new JPanel(null);
		advanced.setBorder(border);
		advanced.setBounds(5, 100, 390, 90);
		advanced.setBackground(Utils.colorImgurDarkGrey());
		container.add(advanced);
		
		threadBoxDescription = new JLabel("Choose the amount of threads");
		threadBoxDescription.setBounds(10, 15, 250, 30);
		threadBoxDescription.setFont(font);
		threadBoxDescription.setHorizontalAlignment(SwingConstants.CENTER);
		threadBoxDescription.setVerticalAlignment(SwingConstants.CENTER);
		threadBoxDescription.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		threadBoxDescription.setVisible(true);
		threadBoxDescription.setForeground(Utils.colorImgurWhite());
		advanced.add(threadBoxDescription);
		
		threadBox = new JComboBox<Integer>(new Integer[]{ 2, 4, 8, 16, 32, 64});
		threadBox.setBounds(280, 18, 100, 25);
		threadBox.setBackground(Utils.colorImgurDarkGrey());
		threadBox.setSelectedIndex(settings.getProgramSettings().getThreadBoxIndex());
		advanced.add(threadBox);
		
		JLabel maxDownloadDescription = new JLabel("Maximum download size in MB");
		maxDownloadDescription.setBounds(10, 50, 250, 30);
		maxDownloadDescription.setFont(font);
		maxDownloadDescription.setHorizontalAlignment(SwingConstants.CENTER);
		maxDownloadDescription.setVerticalAlignment(SwingConstants.CENTER);
		maxDownloadDescription.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		maxDownloadDescription.setVisible(true);
		maxDownloadDescription.setForeground(Utils.colorImgurWhite());
		advanced.add(maxDownloadDescription);
		
		maxDownloads = new JComboBox<>(new Integer[]{ 50, 100, 150, 200, 250, 300, 350,
													  400, 450, 500, 550 } ); 
		maxDownloads.setSelectedIndex(settings.getProgramSettings().getMaxMegabyteIndex());
		maxDownloads.setBounds(280, 53, 100, 25);
		maxDownloads.setBackground(Utils.colorImgurDarkGrey());
		advanced.add(maxDownloads);
		
		Border border2 = BorderFactory.createTitledBorder(null, "Storage management", TitledBorder.LEFT, TitledBorder.TOP, font2, Utils.colorImgurGreen());
		JPanel directories = new JPanel(null);
		directories.setBorder(border2);
		directories.setBounds(5, 200, 390, 160);
		directories.setBackground(Utils.colorImgurDarkGrey());
		container.add(directories);
		
		saveFound = new JCheckBox("Save hashes of the found images");
		saveFound.setToolTipText("Saves the names of the found images to a file");
		saveFound.setSelected(settings.getProgramSettings().isSaveFoundHashes());
		saveFound.setBounds(5, 19, 375, 30);
		saveFound.setFont(font);
		saveFound.setForeground(Utils.colorImgurWhite());
		directories.add(saveFound);
		
		saveNotFound = new JCheckBox("Save hashes which result in no image");
		saveNotFound.setToolTipText("Saves the hashes of the found images to a file");
		saveNotFound.setSelected(settings.getProgramSettings().isSaveNotFoundHashes());
		saveNotFound.setBounds(5, 49, 375, 30);
		saveNotFound.setFont(font);
		saveNotFound.setForeground(Utils.colorImgurWhite());
		directories.add(saveNotFound); 
		
		changeDic1 = new JButton(new ImageIcon(ResourceImage.dic));
		changeDic1.setBounds(15, 79, 20, 20);
		changeDic1.addActionListener(actionListener);
		directories.add(changeDic1);
		
		String dic1 = settings.getDirectorySettings().getPathForHashes().getAbsolutePath();
		saveHashDes = new JLabel(dic1);
		saveHashDes.setBounds(50, 74, 375, 30);
		saveHashDes.setFont(font);
		saveHashDes.setToolTipText(dic1);
		saveHashDes.setForeground(Utils.colorImgurWhite());
		directories.add(saveHashDes);
		
		saveImagePath = new JLabel("The current path to safe images:");
		saveImagePath.setToolTipText("Does not download found images, but tells you if they exists.");
		saveImagePath.setBounds(10, 99, 375, 30);
		saveImagePath.setFont(font);
		saveImagePath.setForeground(Utils.colorImgurWhite());
		directories.add(saveImagePath);
		
		changeDic2 = new JButton(new ImageIcon(ResourceImage.dic));
		changeDic2.setBounds(15, 129, 20, 20);
		changeDic2.addActionListener(actionListener);
		directories.add(changeDic2);
		
		String dic2 = settings.getDirectorySettings().getPathForImages().getAbsolutePath();
		saveImageDes = new JLabel(dic2);
		saveImageDes.setBounds(50, 124, 375, 30);
		saveImageDes.setFont(font);
		saveImageDes.setToolTipText(dic2);
		saveImageDes.setForeground(Utils.colorImgurWhite());
		directories.add(saveImageDes);
		
		Border border3 = BorderFactory.createTitledBorder(null, "Download management", TitledBorder.LEFT, TitledBorder.TOP, font2, Utils.colorImgurGreen());
		JPanel download = new JPanel(null);
		download.setBorder(border3);
		download.setBounds(5, 370, 390, 55);
		download.setBackground(Utils.colorImgurDarkGrey());
		container.add(download);
		
		onlyCheckNotDownload = new JCheckBox("Only check if image exists.");
		onlyCheckNotDownload.setToolTipText("Does not download found images, but tells you if they exists.");
		onlyCheckNotDownload.setSelected(settings.getProgramSettings().isDownloadAllowed());
		onlyCheckNotDownload.setBounds(5, 19, 375, 30);
		onlyCheckNotDownload.setFont(font);
		onlyCheckNotDownload.setForeground(Utils.colorImgurWhite());
		download.add(onlyCheckNotDownload);
		
		ok = new JButton("OK");
		ok.setToolTipText("Use the current settings and close the window");
		ok.setBounds(5, 440, 190, 30);
		ok.setFont(font);
		container.add(ok);
		
		abort = new JButton("Abort");
		abort.setToolTipText("Closes the window and dont use the new settings");
		abort.setBounds(200, 440, 190, 30);
		abort.setFont(font);
		container.add(abort);
		
		defaults = new JButton("Restore defaults");
		defaults.setToolTipText("Restores the settings to the default imgur discoverer settings");
		defaults.setBounds(5, 480, 390, 30);
		defaults.setFont(font);
		container.add(defaults);
			
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub	
	}
	
	private void addActionListeners(){
		ok.addActionListener( (e) -> {
			if ( !ProgramMonitor.isDownloadersAreRunning() ) {
				settings.getProgramSettings().setThreads((int)threadBox.getSelectedItem());
				settings.getProgramSettings().setThreadIndex();
				settings.getProgramSettings().setSaveFoundHashes(saveFound.isSelected());
				settings.getProgramSettings().setSaveNotFoundHashes(saveNotFound.isSelected());
				settings.getProgramSettings().setIsDownloadAllowed(onlyCheckNotDownload.isSelected());
				
				settings.getProgramSettings().setMaxMegabyte((int) maxDownloads.getSelectedItem());
				System.out.println((int) maxDownloads.getSelectedItem());
				InformationPanel.getBar().setString("0.0 mb / "	+ (int) maxDownloads.getSelectedItem() + " mb");
				settings.getProgramSettings().setMaxDownloadsIndex();
				System.out.println(settings.toStaticString());
				settings.createSettingsFile();
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
			if ( !ProgramMonitor.isDownloadersAreRunning() ) {
				threadBox.setSelectedIndex(2);
				settings.getProgramSettings().setThreads((int)threadBox.getSelectedItem());
				
				saveFound.setSelected(true);
				settings.getProgramSettings().setSaveFoundHashes(saveFound.isSelected());
				
				saveNotFound.setSelected(false);
				settings.getProgramSettings().setSaveNotFoundHashes(saveNotFound.isSelected());
				
				onlyCheckNotDownload.setSelected(false);
				settings.getProgramSettings().setIsDownloadAllowed(onlyCheckNotDownload.isSelected());
				
				maxDownloads.setSelectedIndex(2);
				settings.getProgramSettings().setMaxMegabyte(150);
				InformationPanel.getBar().setString("0.0 mb / "	+ 150 + " mb");
				
				System.out.println(settings.toStaticString());
			} else {
				JOptionPane.showMessageDialog(parent, 
						"Can't apply settings while download is in process.");
			} 
				
		});
		
	}
	
}
