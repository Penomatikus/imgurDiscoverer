package imgurDiscoverer.frontent.componets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;



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
	 * X COordinate to translate the {@link ImagePanel#image}. See {@link Graphics2D#translate(double, double)}
	 */
	private double transX;
	/**
	 * Y COordinate to translate the {@link ImagePanel#image}. See {@link Graphics2D#translate(double, double)}
	 */
	private double transY;
	/**
	 * The degree of the rotation of the {@link ImagePanel#image}
	 */
	private double degree;
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
	 * Timer used to animate the rotation of the {@link ImagePanel#image}
	 */
	private Timer timer;
	/**
	 * Used to compare with {@link ImagePanel#degree} and passed to {@link ImagePanel#rotate(double)}.
	 * Please see {@link ImagePanel#actionListener} 
	 */
	private double tmpDegree;
	/**
	 * Indicates if a rotation was done. Used in {@link ImagePanel#actionListener} to check when the rotation
	 * should be rotate in the other direction.
	 */
	private boolean rotateWasDone;
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
	 * {@link ActionListener} used in {@link ImagePanel#timer} to full fill the rotation animation. 
	 */
	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ( rotateWasDone  ) {
				if (tmpDegree > 0)
					rotate(tmpDegree -= 2.0);
				else {
					rotateWasDone = false;
					timer.stop();
				}					
			} else {
				if (tmpDegree < degree)
					rotate(tmpDegree += 2.0);
				else {
					rotateWasDone = true;
					timer.stop();
				}
			}	
		}
	};
	
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
		this.transX = 0;
		this.transY = 0;
		this.degree = 0;
		this.tmpDegree = 0.0;
		this.rotateWasDone = false;
		this.timer = new Timer(1, actionListener);
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
		add(new JLabel(new ImageIcon(imageUrl)));
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
//		WeakReference<Image> reference = new WeakReference<Image>(image);
//		image = null;
		JPanel pane = new JPanel() {
			@Override
			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(0, 0, 220, 220);
			}
			
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, image.getWidth(null), image.getWidth(null), this);
            }
        };
        add(pane);
	}
	
	public void release(){
//		Stopwatch stopwatch = new Stopwatch(3);
//		stopwatch.go();
//		while ( !stopwatch.isDone() );
//        image.flush();	
//        image = null;
	}
//	
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.drawImage(image, 0, 0, null);
//		Graphics2D g2d = (Graphics2D)g;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
//			
////			From lamacore. not needed here
////			if ( rotate )
////				g2d.setPaint(Utils.defaultSystemColor());
////			else
////				
//		g2d.setPaint(new Color(0,0,0,0));				
//		g2d.fill(new Rectangle2D.Double(0, 0, width, height));
//		g2d.translate(transX,transY); // Translate the center of our coordinates.
//	    g2d.rotate(Math.toRadians(tmpDegree));  // Rotate the image by degree
//	    g2d.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), this);
//	    g.dispose();
//	}
	//(degree*(image.getWidth(null)*0.5)/45.0)
	
	/**
	 * Starts the rotation of the image. 
	 * Sets the given degree to the objects {@link ImagePanel#degree}
	 * @param degree	degree to rotate
	 */
	public void startRotation(double degree){
		this.rotate = true;
		this.degree = degree;
		timer.start();
	}
	
	/**
	 * Calls {@link ImagePanel#calculateTranslateSettings(double)} and after {@link Component#repaint()}
	 * @param degree	degree to rotate
	 */
	private void rotate(double degree){
		calculateTranslateSettings(degree);
		repaint();
	}
	
	/**
	 * Calculates the {@link ImagePanel#transX} and {@link ImagePanel#transY} to make {@link ImagePanel#image}
	 * rotate around its center.
	 * @param degree	the degree to rotate.
	 */
	private void calculateTranslateSettings(double degree){
		if ( degree <= 90 ) {
			transX = degree * (width/2) / 45;
			transY = 0;
		} else {
			throw new IllegalArgumentException("[ImagePanel] Degree was over 90°");
		}
		
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
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the width of the panel
	 * @return the width of the panel
	 */
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
}
