package imgurDiscoverer.frontent.frameextra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import imgurDiscoverer.backend.Utils;
import imgurDiscoverer.frontent.componets.ControlPanel;
import imgurDiscoverer.frontent.componets.ImageBox;
import imgurDiscoverer.frontent.componets.ImageBoxArea;
import imgurDiscoverer.frontent.componets.InformationPanel;

public class ProgramWindow extends JFrame implements Window {

	private static final long serialVersionUID = 1L;
	private ImageBoxArea imageBoxArea;
	
	public ProgramWindow(String title, int width, int height) {
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
		Utils.setSystemLookAndFeel();
		Utils.setJVMArgs();
		initComponents();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void initComponents() {
		imageBoxArea = new ImageBoxArea(0, 0, getWidth(), getHeight());
		// some testcases
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 1"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 2"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 3"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 4"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 5"));		
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 6"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 7"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 8"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 9"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 10"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 11"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 12"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 13"));
		imageBoxArea.addBox(new ImageBox("Ich bin Nummer 14"));
		JScrollPane scrollPane = new JScrollPane(imageBoxArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setForeground(Utils.colorImgurLightGrey());
		scrollPane.setViewportBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(32);
		add(scrollPane, BorderLayout.CENTER);
		
		add(new ControlPanel(imageBoxArea.getBoxes()), BorderLayout.PAGE_START);
		
		add(new InformationPanel(), BorderLayout.PAGE_END);

		
		

	}

	@Override
	public void launch() {
		new ProgramWindow("Imgur Discoverer", 1024, 768);
	}

}
