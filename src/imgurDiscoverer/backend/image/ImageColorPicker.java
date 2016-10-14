package imgurDiscoverer.backend.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageColorPicker {

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
