package imgurDiscoverer.frontent.frameextra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import imgurDiscoverer.backend.logic.ImageData;
import imgurDiscoverer.backend.resources.ResourceImage;
import imgurDiscoverer.backend.settings.Settings;
import imgurDiscoverer.backend.utilities.Utils;
import imgurDiscoverer.frontent.componets.ImagePanel;

public class PreviewWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private ImageData imageData;
	private ImagePanel imagePanel;
	private WeakReference<BufferedImage> imageReference;
	private JPanel info;
	private JPanel rootPane;
	private Color commonColor;
	
	private WindowListener windowListener = new WindowListener() {
		
		@Override
		public void windowOpened(WindowEvent e) { }
		
		@Override
		public void windowIconified(WindowEvent e) { }
		
		@Override
		public void windowDeiconified(WindowEvent e) { }
		
		@Override
		public void windowDeactivated(WindowEvent e) { }
		
		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("[PreviewWindow] release memory in heap.");
			imageReference.get().flush();
			imageReference.clear();
			imagePanel.release(true);
			rootPane.removeAll();
			dispose();	
			try { finalize(); } 
			catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		
		@Override
		public void windowClosed(WindowEvent e) {	}
		
		@Override
		public void windowActivated(WindowEvent e) { }
	};
	
	public PreviewWindow(ImageData imageData, Color commonColor) {
		this.imageData = imageData;
		this.commonColor = commonColor;
		this.rootPane = new JPanel();
		setLayout(new BorderLayout());
		double[] display = Utils.displaySize();
		setMinimumSize(new Dimension(1024, 768));
		setMaximumSize(new Dimension((int)display[0], (int)display[1]));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ResourceImage.programIcon));
		int xLoc = (int)display[0] / 2 - (int)getSize().getWidth() / 2;
		int yLoc = (int)display[1] / 2 - (int)getSize().getHeight() / 2;
		setLocation(xLoc, yLoc);
		setBackground(commonColor);
		loadImageFromFile();
		initComponents();
		setVisible(true);
		addWindowListener(windowListener);
	}
	
	private void initComponents(){
		BoxLayout boxLayout = new BoxLayout(rootPane, BoxLayout.X_AXIS);
		rootPane.setMinimumSize(new Dimension(1024, 768));
		rootPane.setLayout(boxLayout);
		rootPane.setBackground(Utils.colorImgurDarkGrey());
		add(rootPane);
		
		info = new JPanel();
		BoxLayout layout = new BoxLayout(info, BoxLayout.Y_AXIS);
		info.setLayout(layout);
		info.setMinimumSize(new Dimension(300, getHeight() - 50));
		info.setPreferredSize(new Dimension(300, getHeight() - 50));
		
		Border border = BorderFactory.createTitledBorder(null, "Image information", 
				TitledBorder.LEFT, TitledBorder.TOP, 
				Utils.defaultFontPlain(15), Utils.colorImgurGreen());
		info.setBorder(border);
		info.setBackground(Utils.colorImgurDarkGrey());
		rootPane.add(info, BorderLayout.LINE_START);
		
		createAndAddInfoPanel("Image hash: ", imageData.getName());
		createAndAddInfoPanel("Image resolution: ", imageReference.get().getHeight() + "x" + imageReference.get().getWidth());
		createAndAddInfoPanel("Common color: ", commonColor.getRed() + ", " +
												commonColor.getGreen() + ", " + 
												commonColor.getBlue() + " [RGB]");
		createAndAddInfoPanel("File size in kb: ", imageData.getFileSize()+"");
		
		imagePanel = new ImagePanel(imageReference.get(), 0, 0);
		JScrollPane scrollPane = new JScrollPane(imagePanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(commonColor);
		scrollPane.setAlignmentX(SwingConstants.LEFT);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		scrollPane.setDoubleBuffered(true);
		rootPane.add(scrollPane, BorderLayout.CENTER);
	}
	
	private void loadImageFromFile(){
		Settings settings = Settings.createSettings();
		try {
			String path = settings.getDirectorySettings().getPathForImages().getAbsolutePath() + 
					File.separator + imageData.getName() + "." +imageData.getExtension();
			imageReference = new WeakReference<BufferedImage>(ImageIO.read(new File(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		System.out.println((int)getSize().getWidth());
//		imagePanel.setLocation((int)getSize().getWidth() / 2 - imageReference.get().getWidth() /2,
//				(int)getSize().getHeight() / 2 - imageReference.get().getHeight() / 2);
//		
//	}
	
	private void createAndAddInfoPanel(String labelName, String textAreaText){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setMaximumSize(new Dimension(300, 60));
		panel.setBackground(Utils.colorImgurDarkGrey());
		
		JLabel label = new JLabel(labelName);
		label.setFont(Utils.defaultFontBold(15));
		label.setForeground(Utils.colorImgurWhite());
		panel.add(label);
		
		JTextArea area = new JTextArea(textAreaText);
		area.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 5));
		area.setCaretPosition(0);
		area.setFocusable(false);
		area.setPreferredSize(new Dimension(200, 25));
		panel.add(area);
		info.add(panel);
	}

}
