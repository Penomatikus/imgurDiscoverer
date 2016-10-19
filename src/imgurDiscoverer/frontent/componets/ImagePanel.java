package imgurDiscoverer.frontent.componets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * <b>EXTRACTED FROM</b> <a href="https://github.com/Penomatikus">Penomatikus[at]GitHub</a> -> Lamacore library <br><br>
 * 
 * Provides an object holding an specific image on a {@link JPanel} with the size of that image.
 * The {@link JPanel} its self  is transparent so the image will be the only visible in that case. 
 * Anyway, in case the image rotates a background rectangle with either the default {@link JPanel} 
 * background color of the system or may the color of the {@link ImagePanel} its self will be used 
 * to paint over all the viewable area. This will be  done in every rotation state until the rotation
 * is finished. Moreover, after that the {@link ImagePanel#image} will be redrawn in another degree. 
 * 
 * @author Stefan Jagdmann <br> <a href="https://github.com/Penomatikus">[at]GitHub</a>
 *
 */
public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 72927643683411056L;
	/**
	 * X coordinate of the {@link ImagePanel}
	 */
	private int x_cord;
	/**
	 * Y coordinate of the {@link ImagePanel}
	 */
	private int y_cord;
	/**
	 * Height of the {@link ImagePanel}
	 */
	private int height;
	/**
	 * Width of the {@link ImagePanel}
	 */
	private int width;
	/**
	 * The {@link Image} that will be drawn 
	 */
	private Image image;
	/**
	 * The {@link URL} an {@link Image} can be loaded from
	 */
	@SuppressWarnings("unused")
	private URL imageUrl;
	/**
	 * Indicates that the image panel is rotating
	 */
	@SuppressWarnings("unused")
	private boolean rotate;
	/**
	 * Default background color.
	 */
	private Color backgroundColor;
	
	/**
	 * Default constructor of an {@link ImagePanel}. 
	 * You may want: <br>
	 * <li> {@link ImagePanel#ImagePanel(Image, int, int)}<br>
	 * <li> {@link ImagePanel#ImagePanel(BufferedImage, int, int)}<br>
	 * <li> {@link ImagePanel#ImagePanel(URL, int, int)}<br>
	 * to actually paint an image.
	 * @param x_Cord	the x coordinate of the {@link ImagePanel}
	 * @param y_Cord	the y coordinate of the {@link ImagePanel}
	 */
	public ImagePanel(int x_Cord, int y_Cord){
		this.x_cord = x_Cord;
		this.y_cord = y_Cord;
		setBackground(new Color(0,0,0,0));
		setVisible(true);
	}
	
	/**
	 * Constructor of an {@link ImagePanel} object. The to draw {@link Image} can be 
	 * passed directly to this constructor. 
	 * @param image		the {@link Image} to draw
	 * @param x_cord	the x coordination where the {@link ImagePanel} should appear
	 * @param y_cord	the y coordination where the {@link ImagePanel} should appear
	 */
	public ImagePanel(Image image, int x_cord, int y_cord) {
		this(x_cord, y_cord);
		this.image = image;
		replaceImage(image);
	}
	
	/**
	 * Constructor of an {@link ImagePanel} object. The to draw {@link Image} can be
	 * passed over an {@link URL} referring to a local resource like a file.
	 * @param image		the {@link Image} to draw
	 * @param x_cord	the x coordination where the {@link ImagePanel} should appear
	 * @param y_cord	the y coordination where the {@link ImagePanel} should appear
	 */
	public ImagePanel(URL imageUrl, int x_cord, int y_cord) {
		this(x_cord, y_cord);
		this.imageUrl = imageUrl;
		replaceImage(imageUrl);
	}
	
	/**
	 * Constructor of an {@link ImagePanel} object. The to draw {@link Image} can be
	 * passed over a {@link BufferedImage}.
	 * @param image		the {@link Image} to draw
	 * @param x_cord	the x coordination where the {@link ImagePanel} should appear
	 * @param y_cord	the y coordination where the {@link ImagePanel} should appear
	 */
	public ImagePanel(BufferedImage bufferedImage, int x_cord, int y_cord) {
		this(x_cord, y_cord);
		replaceImage(bufferedImage);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		image.setAccelerationPriority(1);
		g.drawImage(image, 0, 0, null);
	    g.dispose();
	}

	/**
	 * Returns the x coordinate of the panel
	 * @return the x coordinate of the panel
	 */
	public int getX_cord() {
		return x_cord;
	}

	/**
	 * Returns the y coordinate of the panel
	 * @return the y coordinate of the panel
	 */
	public int getY_cord() {
		return y_cord;
	}

	/**
	 * Returns the height of the panel
	 * @return the height of the panel
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the width of the panel
	 * @return the width of the panel
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the image of the panel
	 * @return the image of the panel
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Returns the default background color
	 * @return
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public void setBackground(Color bg) {
		this.backgroundColor = bg;
		super.setBackground(backgroundColor);
	}
	
	/**
	 * Relocate this {@link ImagePanel} to another location
	 * @param xCord	the new x coordinate
	 * @param yCord	the new y coordinate
	 */
	public void relocate(int xCord, int yCord){
		setBounds(xCord, yCord, getWidth(), getHeight());
	}
	
	/**
	 * Sets the size of this panel
	 */
	private void setSize(){
		this.width = new ImageIcon(image).getIconWidth();
		this.height = new ImageIcon(image).getIconHeight();
		setPreferredSize(new Dimension(width, height));
		setBounds(x_cord, y_cord, width, height);
		repaint();
	}
	
	/**
	 * Replaces the current {@link Image} with a new {@link Image}.
	 * In addition it calls {@link ImagePanel#setSize()}<br>
	 * NOTE: X and Y stays the same. You may want to {@link ImagePanel#relocate(int, int)}
	 * this panel.
	 * @param image	a new {@link Image}
	 */
	public void replaceImage(Image image){
		this.image = image;
		setSize();
	}
	
	/**
	 * Replaces the current {@link Image} with a new {@link Image} from
	 * an {@link URL} resource. In addition it calls {@link ImagePanel#setSize()}<br>
	 * NOTE: X and Y stays the same. You may want to {@link ImagePanel#relocate(int, int)}
	 * this panel.
	 * @param resource	the resource of the new {@link Image}
	 */
	public void replaceImage(URL resource){
		this.image = Toolkit.getDefaultToolkit().getImage(resource);
		setSize();
	}
	
	/**
	 * Replaces the current {@link Image} with a new {@link Image} from
	 * an {@link BufferedImage}. In addition it calls {@link ImagePanel#setSize()}<br>
	 * NOTE: X and Y stays the same. You may want to {@link ImagePanel#relocate(int, int)}
	 * this panel.
	 * @param bufferedImage	the new {@link BufferedImage}
	 */
	public void replaceImage(BufferedImage bufferedImage) {
		this.image = bufferedImage;
		setSize();
	}
	
	public void release(boolean callFinalize){
		image.flush();
		image = null;
		if ( callFinalize ) {
			try { finalize(); }
			catch ( Throwable t ) {
				t.printStackTrace();
			}
		}
	}
}
