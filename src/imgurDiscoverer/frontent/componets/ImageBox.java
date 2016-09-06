package imgurDiscoverer.frontent.componets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import imgurDiscoverer.backend.ResourceURL;
import imgurDiscoverer.backend.Utils;

/**
 * Provides an object, for displaying an ( downloaded ) image-resource. <br>
 * It comes with some UI features like, changing the color of its grey dashed
 * border to Imgurs green (RGB: 57, 196, 66 ) and showing a little "is selected"
 * icon in the upper right corner, if clicked. <br>
 * Moreover, the displayed images will not be resized if the {@link ImageBox} its
 * self got resized, however the image will always be in the center of the box and
 * the free space to the right and left will be filled with the avarage color of the
 * image its self. <br>
 * <b>Usage:</b>
 * <pre>
 * 	<code>
 * 		ImageBox imageBox = new ImageBox("my resource name"):
 * 	</code>
 * </pre>
 * The resource name should have the same name, as the image resource has.
 * @author stefan
 *
 */
public class ImageBox extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 230;
	public static final int HEIGHT = 230;
	/**
	 * The resource name of the image to display in the box
	 */
	private String resource;
	private JPanel descriptionPanel;
	public boolean isSelected;
	private MouseAdapter mouseAdapter;
	private ImagePanel selected;
	
	public ImageBox(String resource) {
		this.resource = resource;
		this.isSelected = false;
		
		setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2.0f, 1.8f, 1.2f, true));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(new Color(52, 55, 60 ));
		setLayout(new BorderLayout());
		setDoubleBuffered(true);
		initMouseAdapter();
		initComponents();
	}
	
	private void initMouseAdapter(){
		mouseAdapter = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( !isSelected ) {
					isSelected = true;
					setBorder(BorderFactory.createDashedBorder(Utils.colorImgurGreen(), 3.0f, 1.8f, 1.2f, true));
					selected.setVisible(true);
				} else {
					isSelected = false;
					setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2.0f, 1.8f, 1.2f, true));
					selected.setVisible(false);
				}
					
			}		
		};
		
		addMouseListener(mouseAdapter);
	}
	
	private void initComponents(){
		/*
		 * add image here 
		 */
		
		descriptionPanel = new JPanel(new FlowLayout());
		descriptionPanel.setBackground(new Color(140, 140, 140, 30));
		add(descriptionPanel, 0);
		
		JLabel description = new JLabel(resource);
		description.setBorder(new EmptyBorder(10, 10, 10, 10));
		description.setFont(new Font("SansSerif", Font.PLAIN, 18));
		description.setHorizontalAlignment(JLabel.CENTER);
		description.setVerticalAlignment(JLabel.CENTER);
		descriptionPanel.add(description);
		
		descriptionPanel.validate();
		descriptionPanel.repaint();
		
		selected = new ImagePanel(ResourceURL.selected, 0, 0);
		selected.setVisible(false);
		add(selected, BorderLayout.EAST);
	}
		
	/**
	 * DEM HACKS!!
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		descriptionPanel.setBounds(2, (int)getPreferredSize().getHeight() - 50, (int)getSize().getWidth(), 100);
	}
	
	public String getResourceName(){
		return resource;
	}
	
	public boolean isSelected(){
		return isSelected;
	}
	
	
	
	

}
