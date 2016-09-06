package imgurDiscoverer.frontent.componets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Provides an object, which holds all {@link ImageBox}es. Those
 * are placed with a maximum amount of four on a {@link RowPanel}.
 * If the amount of {@link ImageBox}es modulo four is zero, a new {@link RowPanel}
 * will be added and all new incoming {@link ImageBox}es will be 
 * placed there.  <br>
 * <b>Usage:</b> <br> 
 * <pre>
 * 	<code>
 * 		ImageBoxArea area = new ImageBoxArea(0, 0, 300, 200);
 * 		area.addBox(new ImageBox("My Box 1");
 *  </code>
 * </pre>
 * The {@link ImageBoxArea} will guarantee, that the {@link ImageBox} 
 * will become well placed. <br>  
	
 * @author Stefan Jagdmann aká Penomatikus
 *
 */
public class ImageBoxArea extends JPanel{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Holds all {@link ImageBox} objects
	 */
	private List<ImageBox> boxes;
	/**
	 * Holds all {@link RowPanel} objects
	 */
	private List<RowPanel> rows;

	/**
	 * Provides an object, which holds all {@link ImageBox}es. Those
	 * are placed with a maximum amount of four on a {@link RowPanel}.
	 * If the amount of {@link ImageBox}es modulo four is zero, a new {@link RowPanel}
	 * will be added and all new incoming {@link ImageBox}es will be 
     * placed there.  <br>
  	 * <b>Usage:</b> <br> 
 	 * <pre>
 	 * 	<code>
 	 * 		ImageBoxArea area = new ImageBoxArea(0, 0, 300, 200);
 	 * 		area.addBox(new ImageBox("My Box 1");
 	 *  </code>
 	 * </pre>
 	 * The {@link ImageBoxArea} will guarantee, that the {@link ImageBox} 
 	 * will become well placed. <br>  
		
	 * @param xCord		The x coordinate
	 * @param yCord		The y coordinate
	 * @param width		The width 
	 * @param height	The height
	 */
	public ImageBoxArea(int xCord, int yCord, int width, int height) {
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBounds(xCord, yCord, width, height);
		setBackground(new Color(20, 21, 24));
		setDoubleBuffered(true);
		setVisible(true);
		initLists();
	}
	
	/**
	 * Initiates the lists...
	 */
	private void initLists(){
		boxes = new ArrayList<>();
		rows = new ArrayList<>();
	}
	
	/**
	 * Adds a new {@link ImageBox} to the {@link ImageBoxArea}. <br><br>
	 * <b>Side note: </b>
	 * If the amount of {@link ImageBox}es modulo four is zero, a new {@link RowPanel}
	 * will be added and all new incoming {@link ImageBox}es will be 
     * placed there. 
	 * @param box	The {@link ImageBox} to add
	 */
	public void addBox(ImageBox box){
		// For the spacing between each imagebox
		JPanel space = new JPanel();
		space.setBackground(new Color(20, 21, 24));
		space.setSize(new Dimension(10, 10));
		
		if ( ( boxes.size() % 4 ) == 0 ){			// do we have more than 4 imageboxes in the current line?
			RowPanel row = new RowPanel(this);		// create new row
			row.add(box);							// add new imagebox to that row
			boxes.add(box);							// add new imagebox to the box list
			rows.add(row);							// add created row to the row list
			rows.get(rows.size()-1).add(space);		// add the space panel to the created row
			add(row);								// add row to the imagebox-area
		} else {
			boxes.add(box);							
			rows.get(rows.size() - 1).add(box);
			rows.get(rows.size() - 1).add(space);
		}
	}
		
	/**
	 * @return {@link ImageBoxArea#boxes}
	 */
	public List<ImageBox> getBoxes() {
		return boxes;
	}

	/**
	 * @return {@link ImageBoxArea#rows}
	 */
	public List<RowPanel> getRows() {
		return rows;
	}


	/**
	 * Provides a {@link JPanel}  using a {@link BoxLayout}  ( with X axis arrangement )
	 * to offer resizability. Moreover its height will not be greater than {@link ImageBox#HEIGHT},
	 * which is 230px. To accomplish this behavior it overwrites paintComponet by adding the line: <br>
	 * <pre>
	 * 	<code>
	 * 		setMaximumSize(new Dimension(parent.getWidth(), ImageBox.HEIGHT));
	 *  </code>
	 * </pre>
	 * Where parent is the {@link ImageBoxArea} object.
	 * @author stefan
	 *
	 */
	private class RowPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;
		/**
		 *  The {@link ImageBoxArea} object 
		 */
		private ImageBoxArea parent;
		
		/**
		 * 	 * Provides a {@link JPanel}  using a {@link BoxLayout}  ( with X axis arrangement )
		 * to offer resizability. Moreover its height will not be greater than {@link ImageBox#HEIGHT},
		 * which is 230px. To accomplish this behavior it overwrites paintComponet by adding the line: <br>
		 * <pre>
		 * 	<code>
		 * 		setMaximumSize(new Dimension(parent.getWidth(), ImageBox.HEIGHT));
		 *  </code>
		 * </pre>
		 * @param parent the {@link ImageBoxArea} object 
		 */
		public RowPanel(ImageBoxArea parent) {
			setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setBackground(new Color(20, 21, 24));
			setDoubleBuffered(true);
			this.parent = parent;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			setMaximumSize(new Dimension(parent.getWidth(), ImageBox.HEIGHT));
		}
	}

}
