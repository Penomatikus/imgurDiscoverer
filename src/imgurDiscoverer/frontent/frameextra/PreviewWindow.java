package imgurDiscoverer.frontent.frameextra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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
	private Color commonColor;
	
	public PreviewWindow(ImageData imageData, Color commonColor) {
		this.imageData = imageData;
		this.commonColor = commonColor;
		
		setLayout(new BorderLayout());
		double[] display = Utils.displaySize();
		setMinimumSize(new Dimension(1024, 768));
		setMaximumSize(new Dimension((int)display[0], (int)display[1]));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ResourceImage.programIcon));
		int xLoc = (int)display[0] / 2 - (int)getSize().getWidth() / 2;
		int yLoc = (int)display[1] / 2 - (int)getSize().getHeight() / 2;
		setLocation(xLoc, yLoc);
		setBackground(commonColor);
		loadImageFromFile();
		initComponents();
		setVisible(true);
	}
	
	private void initComponents(){
		info = new JPanel(new GridBagLayout());
		info.setPreferredSize(new Dimension(300, getHeight() - 50));
		Font font2 = new Font("SansSerif", Font.PLAIN, 15);
		Border border = BorderFactory.createTitledBorder(null, "Image information", TitledBorder.LEFT, TitledBorder.TOP, font2, Utils.colorImgurGreen());
		info.setBorder(border);
		info.setBackground(Utils.colorImgurDarkGrey());
		add(info, BorderLayout.LINE_START);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.ipady = 5;
		constraints.ipadx = 5;
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		
		Border empty = BorderFactory.createEmptyBorder(0, 5, 5, 5);
		Font font = new Font("SansSerif", Font.BOLD, 15);
		
		JLabel imageNameDes = new JLabel("Image hash: ");
		imageNameDes.setBorder(empty);
		imageNameDes.setFont(font);
		imageNameDes.setForeground(Utils.colorImgurLightGrey());
		info.add(imageNameDes, constraints);
		constraints.gridx = 1;
		
		JTextField imageName = new JTextField(imageData.getName(), 1);
		imageName.setBorder(empty);
		imageName.setCaretPosition(0);
		imageName.setMinimumSize(new Dimension(100, 30));
		imageName.setFocusable(false);
		info.add(imageName, constraints);
		
		JLabel resolutionDes = new JLabel("Image resolution: ");
		resolutionDes.setBorder(empty);
		resolutionDes.setFont(font);
		resolutionDes.setForeground(Utils.colorImgurLightGrey());
		constraints.gridx = 0;
		constraints.gridy = 2;
		info.add(resolutionDes, constraints);
		
		JTextField resolution = new JTextField(image.getHeight() + "x" + image.getWidth(), 1);
		resolution.setBorder(empty);
		resolution.setCaretPosition(0);
		resolution.setPreferredSize(new Dimension(200, 30));
		resolution.setFocusable(false);
		constraints.gridx = 1;
		constraints.gridy = 2;
		info.add(resolution, constraints);
		
		JLabel colorDes = new JLabel("Common color: ");
		colorDes.setBorder(empty);
		colorDes.setFont(font);
		colorDes.setBackground(Utils.colorImgurLightGrey());
		constraints.gridx = 0;
		constraints.gridy = 3;
		info.add(colorDes, constraints);
		
		JTextField color = new JTextField(commonColor.getRed() + ", " +
										  commonColor.getGreen() + ", " + 
										  commonColor.getBlue() + " [RGB]", 1);
		color.setBorder(empty);
		color.setCaretPosition(0);
		color.setFocusable(false);
		color.setMinimumSize(new Dimension(100, 30));
		constraints.gridx = 1;
		constraints.gridy = 3;
		info.add(color, constraints);
		imagePanel = new ImagePanel(image, 0, 0);
		JScrollPane scrollPane = new JScrollPane(imagePanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(commonColor);
		add(scrollPane, BorderLayout.CENTER);
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
