package imgurDiscoverer.backend.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ImageDownScaler {

	private static final int IMAGE_WIDTH = 220;
	private static final int IMAGE_HEIGHT = IMAGE_WIDTH;
	/**
	 * The image to down scale  
	 */
	private static BufferedImage bufferedImage;
	/**
	 * 
	 */
	private static WeakReference<BufferedImage> reference;
	private static BufferedImage thumbnail;
	
	public ImageDownScaler(BufferedImage bufferedImage) {
		ImageDownScaler.bufferedImage = bufferedImage;
	}
	
	public synchronized static BufferedImage downScale(BufferedImage bufferedImage){
		reference = new WeakReference<BufferedImage>(bufferedImage);
		
		thumbnail = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
			
        Graphics2D g = thumbnail.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(reference.get(), 0, 0, 220, 220, null);
        try {  reference.get().flush(); }
        catch (Exception e ) { ; }
        g.dispose();
        return thumbnail;
	}
}
