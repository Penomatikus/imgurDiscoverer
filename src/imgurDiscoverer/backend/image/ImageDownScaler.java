package imgurDiscoverer.backend.image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;

/**
 * Provides a class, to resize a given {@link BufferedImage} to a fixed size of 220x220. <br>
 * Since this procedure might be time and resource expensive, the only method 
 * {@link ImageDownScaler#downScale(BufferedImage)} is blocking. <br>
 * Moreover, due to memory ( heap ) management, the provides {@link BufferedImage} will be
 * referenced by an {@link WeakReference} object and becomes null, right after the reference. 
 * So the {@link ImageDownScaler} might return null in some very super rare cases. 
 * 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class ImageDownScaler {

	/**
	 * The fixed width of the new image
	 */
	private static final int IMAGE_WIDTH = 220;
	/**
	 * The fixed height of the new image
	 */
	private static final int IMAGE_HEIGHT = IMAGE_WIDTH;
	
	/**
	 * Resizes a given {@link BufferedImage} to a thumbnail version of 220x220px.
	 * The resource image becomes referenced by an {@link WeakReference} object and right after 
	 * it will be declared with null.<br> In addition the thumbnail will be rendered out of
	 * the reference data ( which is a copy of the given image ). <br>
	 * The used render hints are: <br> - {@link RenderingHints#KEY_INTERPOLATION} and <br>
	 * - {@link RenderingHints#VALUE_INTERPOLATION_NEAREST_NEIGHBOR}.<br>
	 * After the rendering process, 
	 * the reference will be flushed, cleared and becomes null, so the GC is informed to clear all left over
	 * data.
	 * @param bufferedImage	The image to resize
	 * @return	A thumbnail version of the image in 220x220 px
	 */
	public synchronized static BufferedImage downScale(BufferedImage bufferedImage){
		WeakReference<BufferedImage> reference = new WeakReference<BufferedImage>(bufferedImage);
		bufferedImage = null;
		BufferedImage thumbnail = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = thumbnail.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(reference.get(), 0, 0, 220, 220, null);
        try {  reference.get().flush(); reference.clear(); reference = null; }
        catch (Exception e ) { ; }
        g.dispose();
        return thumbnail;
	}
}
