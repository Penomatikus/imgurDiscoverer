package imgurDiscoverer.backend.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a class, to calculate the most common color of an 
 * {@link BufferedImage}. <br> Since this work might by a time intensive
 * and resource intensive procedure, the only method
 * {@link ImageColorPicker#mostCommonColor(BufferedImage)} is blocking.
 * 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class ImageColorPicker {

	/**
	 * Calculates the most common color of an image by mapping each
	 * pixel color of the image and increasing the counter of a color
	 * by one, if it appears.
	 * @param image	The image to get the most common color
	 * @return	The most common color
	 */
	public synchronized static Color mostCommonColor(BufferedImage image){
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
		 colorMap.clear();
		 colorMap = null;
		 return mostCommon;
	}
}
