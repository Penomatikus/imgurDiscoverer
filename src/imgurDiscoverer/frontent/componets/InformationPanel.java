package imgurDiscoverer.frontent.componets;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import imgurDiscoverer.backend.utilities.Utils;

public class InformationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JProgressBar bar;
	
	public InformationPanel() {
		setLayout(new BorderLayout());
		setBackground(Utils.colorImgurDarkGrey());
		setPreferredSize(new Dimension(100, 30));
		setBorder(BorderFactory.createLineBorder(Utils.colorImgurLightGrey()));
		initCompontents();
	}
	
	private void initCompontents(){
		bar = new JProgressBar();
		//bar.setIndeterminate(true);
		bar.setToolTipText("If I'm showing you my little back and forth thing, I'm probably working on stuff.");
		bar.setDoubleBuffered(true);
		add(bar, BorderLayout.CENTER);
	}

}
