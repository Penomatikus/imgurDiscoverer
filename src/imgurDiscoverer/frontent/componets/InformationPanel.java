package imgurDiscoverer.frontent.componets;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import imgurDiscoverer.backend.logic.Singleton;
import imgurDiscoverer.backend.utilities.Utils;

public class InformationPanel extends JPanel implements Singleton {

	private static final long serialVersionUID = 1L;
	private static JProgressBar bar;
	private static InformationPanel self;
	
	private InformationPanel() {
		setLayout(new BorderLayout());
		setBackground(Utils.colorImgurDarkGrey());
		setPreferredSize(new Dimension(100, 20));
		setBorder(BorderFactory.createLineBorder(Utils.colorImgurLightGrey()));
		initCompontents();
	}
	
	public static InformationPanel createInformationPanel(){
		return ( self == null ) ? self = new InformationPanel() : self;
	}
	
	private void initCompontents(){
		bar = new JProgressBar();
		bar.setToolTipText("If I'm showing you my little back and forth thing, I'm probably working on stuff.");
		bar.setDoubleBuffered(true);
		add(bar, BorderLayout.CENTER);
	}
	
	public static JProgressBar getBar(){
		return bar;
	}

}
