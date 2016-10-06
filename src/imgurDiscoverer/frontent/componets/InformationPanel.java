package imgurDiscoverer.frontent.componets;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import imgurDiscoverer.backend.logic.Singleton;
import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.backend.utilities.Utils;

public class InformationPanel extends JPanel implements Singleton {

	private static final long serialVersionUID = 1L;
	private static JProgressBar bar;
	private static InformationPanel self;
	private static JLabel currentTaskDes;
	private JPanel currentTask;
	private static JLabel downloadedImagesDes;
	private JPanel downloadedImages;
	
	private InformationPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Utils.colorImgurDarkGrey());
		setPreferredSize(new Dimension(100, 25));
		setBorder(BorderFactory.createLineBorder(Utils.colorImgurLightGrey()));
		initCompontents();
	}
	
	public static InformationPanel createInformationPanel(){
		return ( self == null ) ? self = new InformationPanel() : self;
	}
	
	private void initCompontents(){
		currentTaskDes = new JLabel("Current task: none");
		currentTaskDes.setToolTipText(currentTaskDes.getText());
		currentTaskDes.setForeground(Utils.colorImgurWhite());
		
		currentTask = new JPanel(new FlowLayout(FlowLayout.LEFT));
		currentTask.setPreferredSize(new Dimension(250, 25));
		currentTask.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		currentTask.setBackground(Utils.colorImgurLightGrey());
		currentTask.add(currentTaskDes);
		add(currentTask);
				
		downloadedImagesDes = new JLabel("Images: 0 / 0 ");
		downloadedImagesDes.setToolTipText("X out of y downloaded images are progressed.");
		downloadedImagesDes.setForeground(Utils.colorImgurWhite());
		
		downloadedImages = new JPanel(new FlowLayout(FlowLayout.LEFT));
		downloadedImages.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		downloadedImages.setMaximumSize(new Dimension(250, 25));
		downloadedImages.setMinimumSize(new Dimension(100, 25));
		downloadedImages.setBackground(Utils.colorImgurLightGrey());
		downloadedImages.add(downloadedImagesDes);
		add(downloadedImages);
		
		bar = new JProgressBar();
		bar.setStringPainted(true);
		String currentMB = ProgramMonitor.getDownloadedMegabyteAtRuntime() + "";
		Settings settings = Settings.createSettings();
		bar.setString(currentMB + " mb / " + settings.getProgramSettings().getMaxMegabyte() + " mb");
		bar.setToolTipText("If I'm showing you my little back and forth thing, I'm probably working on stuff.");
		bar.setDoubleBuffered(true);
		bar.setPreferredSize(new Dimension(500, 25));
		add(bar);
		
	}
	
	public static JLabel getCurrentTaskDes() {
		return currentTaskDes;
	}
	
	public static JLabel getDownloadedImagesDes() {
		return downloadedImagesDes;
	}
	
	public static JProgressBar getBar(){
		return bar;
	}

}
