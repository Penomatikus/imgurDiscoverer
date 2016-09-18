package imgurDiscoverer.backend.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import imgurDiscoverer.frontent.componets.ImageBox;

public class ImageDownScaler {

	/**
	 * The image to down scale  
	 */
	private BufferedImage bufferedImage;
	
	public ImageDownScaler(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public BufferedImage downScale(){
		Image tmp = bufferedImage.getScaledInstance(ImageBox.WIDTH - 10, 
				ImageBox.WIDTH - 10, Image.SCALE_FAST);
		BufferedImage resiszed = new BufferedImage(tmp.getWidth(null), 
				tmp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2d = resiszed.createGraphics();
		graphics2d.drawImage(tmp, 0, 0, null);
		graphics2d.dispose();
		return resiszed;
	}
}
