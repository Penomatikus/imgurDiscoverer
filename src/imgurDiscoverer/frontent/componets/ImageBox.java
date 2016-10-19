package imgurDiscoverer.frontent.componets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.ref.SoftReference;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.utilities.Utils;
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
	private static final URL okay = ResourceImage.selected;
	public static final int WIDTH = 230;
	public static final int HEIGHT = 230;
	public ImageData resource;
	private JPanel descriptionPanel;
	private JPanel content;
	public boolean isSelected;
	private MouseAdapter mouseAdapter;
	private ImagePanel selected;
	private Color background;
	private SoftReference<Image> reference;
	
	public ImageBox(ImageData resource) {
		this.resource = resource;
		this.isSelected = false;
		reference = new SoftReference<Image>(resource.createVolatileImage());
		setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2.0f, 1.8f, 1.2f, true));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		if ( reference.get() != null )
			setBackground(background = resource.getMostCommonColor());
		else
			setBackground(background = Utils.colorImgurDarkGrey()); 
		setLayout(new BorderLayout());
		initMouseAdapter();
		initComponents();
		flushImageMemory();
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
		
		if ( resource.createVolatileImage() != null ) {
			ImagePanel panel = new ImagePanel(reference.get(), 0, 0);
			content.add(panel);
		}

		
		descriptionPanel = new JPanel(new FlowLayout());
		descriptionPanel.setBackground(new Color(140, 140, 140, 80));
		add(descriptionPanel, 0);
		
		JLabel description = new JLabel(resource.getName());
		description.setBorder(new EmptyBorder(10, 10, 10, 10));
		description.setFont(new Font("SansSerif", Font.PLAIN, 18));
		description.setHorizontalAlignment(SwingConstants.CENTER);
		description.setVerticalAlignment(SwingConstants.CENTER);
		description.setForeground(new Color(255 - background.getRed(), 
											255 - background.getGreen(),
											255 - background.getBlue()));
		descriptionPanel.add(description);
		
		descriptionPanel.validate();
		descriptionPanel.repaint();
		
		selected = new ImagePanel(ImageBox.okay, 0, 0);
		selected.setVisible(false);
		add(selected, BorderLayout.EAST, 0);
	}
	
	private void flushImageMemory(){
		resource.release();
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
