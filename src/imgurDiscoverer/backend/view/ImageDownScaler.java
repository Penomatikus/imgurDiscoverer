package imgurDiscoverer.backend.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ImageDownScaler {

	/**
	 * The image to down scale  
	 */
	private BufferedImage bufferedImage;
	
	public ImageDownScaler(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public BufferedImage downScale(){
		WeakReference<BufferedImage> reference = new WeakReference<BufferedImage>(this.bufferedImage);
		this.bufferedImage = null;
		
		BufferedImage thumbnail = new BufferedImage(220, 220,
                BufferedImage.TYPE_INT_ARGB);
		SoftReference<BufferedImage> referenceThumb = new SoftReference<BufferedImage>(thumbnail);
		thumbnail = null;
		
        Graphics2D g = referenceThumb.get().createGraphics();
        g.drawImage(reference.get(), 0, 0, 220, 220, null);
        try {  reference.get().flush(); }
        catch (Exception e ) { ; }
        g.dispose();
        return referenceThumb.get();
	}
	
	public void release(){
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
