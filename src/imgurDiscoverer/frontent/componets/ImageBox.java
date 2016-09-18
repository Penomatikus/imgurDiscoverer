package imgurDiscoverer.frontent.componets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.backend.view.ImageDownScaler;
import imgurDiscoverer.frontent.frameextra.PreviewWindow;

/**
 * Provides an object, for displaying an ( downloaded ) image-resource. <br>
 * It comes with some UI features like, changing the color of its grey dashed
 * border to Imgur's green (RGB: 57, 196, 66 ) and showing a little "is selected"
 * icon in the upper right corner, if clicked. <br>
 * Moreover, the displayed images will not be resized if the {@link ImageBox} its
 * self got resized, however the image will always be in the center of the box and
 * the free space to the right and left will be filled with the average color of the
 * image its self. <br>
 * <b>Usage:</b>
 * <pre>
 * 	<code>
 * 		ImageBox imageBox = new ImageBox(new ImageData(myBufferedImage, "image"):
 * 	</code>
 * </pre>
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class ImageBox extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 230;
	public static final int HEIGHT = 230;
	public ImageData resource;
	private JPanel descriptionPanel;
	private JPanel content;
	public boolean isSelected;
	private MouseAdapter mouseAdapter;
	private ImagePanel selected;
	private Color background;
	ImagePanel imagePanel;
	
	public ImageBox(ImageData resource) {
		this.resource = resource;
		this.isSelected = false;
		
		setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2.0f, 1.8f, 1.2f, true));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(background = mostCommonColor());
		setLayout(new BorderLayout());
		setDoubleBuffered(true);
		initMouseAdapter();
		initComponents();
	}
	
	private void initMouseAdapter(){
		mouseAdapter = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( e.getClickCount() == 1 ) {
					if ( !isSelected ) {
						isSelected = true;
						setBorder(BorderFactory.createDashedBorder(Utils.colorImgurGreen(), 3.0f, 1.8f, 1.2f, true));
						selected.setVisible(true);
					} else {
						isSelected = false;
						setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2.0f, 1.8f, 1.2f, true));
						selected.setVisible(false);
					}
				} else if ( e.getClickCount() == 2 ) {
					isSelected = false;
					setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2.0f, 1.8f, 1.2f, true));
					selected.setVisible(false);
					SwingUtilities.invokeLater(() -> {
						new PreviewWindow(resource, background);
					});
				}
					
			}		
		};
		
		addMouseListener(mouseAdapter);
	}
	
	private void initComponents(){
		content = new JPanel();
		content.setSize(new Dimension(WIDTH - 10, HEIGHT -10));
		content.setBackground(new Color(0, 0, 0, 0));
		add(content, BorderLayout.CENTER);
		
		imagePanel = new ImagePanel(new ImageDownScaler(
				resource.getImageData()).downScale(), 0, 0);
		//imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		content.add(imagePanel);

		
		descriptionPanel = new JPanel(new FlowLayout());
		descriptionPanel.setBackground(new Color(140, 140, 140, 80));
		add(descriptionPanel, 0);
		
		JLabel description = new JLabel(resource.getName());
		description.setBorder(new EmptyBorder(10, 10, 10, 10));
		description.setFont(new Font("SansSerif", Font.PLAIN, 18));
		description.setHorizontalAlignment(JLabel.CENTER);
		description.setVerticalAlignment(JLabel.CENTER);
		description.setForeground(new Color(255 - background.getRed(), 
											255 - background.getGreen(),
											255 - background.getBlue()));
		descriptionPanel.add(description);
		
		descriptionPanel.validate();
		descriptionPanel.repaint();
		
		selected = new ImagePanel(ResourceImage.selected, 0, 0);
		selected.setVisible(false);
		add(selected, BorderLayout.EAST, 0);
	}
	
	/**
	 * http://stackoverflow.com/a/28162725
	 * @return
	 */
	private Color mostCommonColor(){
		BufferedImage image = resource.getImageData();
		 Map<Color, Integer> colorMap = new HashMap<Color, Integer>();
		 int x = image.getWidth(); 
		 int y = image.getHeight();
		 int highestOccurence = 0;
		 Color mostCommon = null;
		 for ( int i = 0; i < x; i++ ){
			 for ( int j = 0; j < y; j++ ){
				 Color c = new Color( image.getRGB(i, j) );
				 int k = 0;
				 if ( colorMap.containsKey(c))
					 k = colorMap.get(c);
				 colorMap.put(c, k++);
				 if ( k > highestOccurence ) {
					 highestOccurence = k;
					 mostCommon = c;
				 }
			 }
		 }
		 return mostCommon;
	}
		
	/**
	 * DEM HACKS!!
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int currentHight = (int)getSize().getHeight();
		int currentWidth = (int)getSize().getWidth();
		descriptionPanel.setBounds(2, currentHight - 50, currentWidth, 100);
		content.setLocation(currentWidth / 2 - 110, currentHight / 2 - 110);
	}
	
	public ImageData getResourceName(){
		return resource;
	}
	
	public boolean isSelected(){
		return isSelected;
	}
	
	
	
	

}
