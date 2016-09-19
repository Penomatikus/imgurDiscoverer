package imgurDiscoverer.frontent.frameextra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.frontent.componets.ImagePanel;

public class PreviewWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private ImageData imageData;
	private ImagePanel imagePanel;
	private BufferedImage image;
	private JPanel info;
	
	public PreviewWindow(ImageData imageData, Color commonColor) {
		this.imageData = imageData;
		setIconImage(Toolkit.getDefaultToolkit().getImage(ResourceImage.programIcon));
		double[] display = Utils.displaySize();
		setMinimumSize(new Dimension(1024, 768));
		setMaximumSize(new Dimension((int)display[0], (int)display[1]));
		int xLoc = (int)display[0] / 2 - (int)getSize().getWidth() / 2;
		int yLoc = (int)display[1] / 2 - (int)getSize().getHeight() / 2;
		setLocation(xLoc, yLoc);
		setBackground(commonColor);
		loadImageFromFile();
		initComponents();
		setVisible(true);
	}
	
	private void initComponents(){
		info = new JPanel(new GridLayout(0, 2));
		//info.setMaximumSize(new Dimension(300, (int)Utils.displaySize()[0]));
		info.setBorder(BorderFactory.createTitledBorder("Image information"));
		add(info);
		
		imagePanel = new ImagePanel(image, 0, 0);
		JScrollPane scrollPane = new JScrollPane(imagePanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
	}
	
	private void loadImageFromFile(){
		Settings settings = Settings.createSettings();
		try {
			String path = settings.getDirectorySettings().getPathForImages().getAbsolutePath() + 
					File.separator + imageData.getName() + "." +imageData.getExtension();
			image = ImageIO.read(new File(path));					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		System.out.println((int)getSize().getWidth());
		imagePanel.setLocation((int)getSize().getWidth() / 2 - image.getWidth() /2,
				(int)getSize().getHeight() / 2 - image.getHeight() / 2);
		
	}

}
