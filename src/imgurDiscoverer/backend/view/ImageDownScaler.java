package imgurDiscoverer.backend.view;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;

public class ImageDownScaler {

	/**
	 * The image to down scale  
	 */
	private BufferedImage bufferedImage;
	/**
	 * 
	 */
	private WeakReference<BufferedImage> reference;
	
	public ImageDownScaler(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public BufferedImage downScale(){
		reference = new WeakReference<BufferedImage>(this.bufferedImage);
		this.bufferedImage = null;
		
		BufferedImage thumbnail = new BufferedImage(220, 220,
                BufferedImage.TYPE_INT_RGB);
			
        Graphics2D g = thumbnail.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(reference.get(), 0, 0, 220, 220, null);
        try {  reference.get().flush(); }
        catch (Exception e ) { ; }
        g.dispose();
        return thumbnail;
	}
	
	public void release(){
		try {
			reference.get().flush();
			finalize();
		} catch (Throwable e) {
			if( !( e instanceof NullPointerException ) )
				e.printStackTrace();
		}
	}
}
