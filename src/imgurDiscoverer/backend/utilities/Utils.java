package imgurDiscoverer.backend.utilities;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;

import javax.swing.UIManager;

import imgurDiscoverer.backend.settings.ProgramMonitor;
import imgurDiscoverer.backend.settings.Settings;

public class Utils {
	
	/**
	 * @return Double array holding the width and height of the current display
	 */
	public static double[] displaySize(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screewidth = screenSize.getWidth();
		double screeheight = screenSize.getHeight();
		return new double[]{screewidth, screeheight};
	}
	
	/**
	 * Tries to set the systems look and feel. If not successful, the default
	 * java look and feel will be used. 
	 */
	public static void setSystemLookAndFeel(){
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the JVM arguments
	 * */
	public static void setJVMArgs(){
		System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.noddraw", "true");
	}
	
	public static void prepareStartUp(){
		setSystemLookAndFeel();
		setJVMArgs();
		Settings.createSettings();
		ProgramMonitor.createProgramMonitor();
	}
	
	/**
	 * @return	The systems default color, if available
	 */
	public static Color defaultSystemColor(){
		return UIManager.getColor("Panel.background");
	}
	
	/**
	 * Opens a {@link URI} in the systems default browser.
	 * @param uri
	 */
	public static void openInDefaultBrowser(URI uri){
		if ( Desktop.isDesktopSupported() ){
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Could not open in Default browser.");
		}
	}
	
	public static Color colorImgurGreen(){
		return new Color(57, 196, 66);
	}

	public static Color colorImgurDarkGrey(){
		return new Color(20, 21, 24);
	}
	
	public static Color colorImgurLightGrey(){
		return new Color(52, 55, 60);
	}
	
	
}
