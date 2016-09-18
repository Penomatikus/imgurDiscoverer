package imgurDiscoverer.frontent.componets;


import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;


public class IntelligentTextfield extends JTextField implements KeyListener {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String input;

	public IntelligentTextfield(int xCord, int yCord, int width, int height ) {
		setBounds(xCord, yCord, width, height);
		setBackground(Color.WHITE);
		addKeyListener(this);
	}

		
	@Override
	public void keyTyped(KeyEvent e) {
		checkInput(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		checkInput(e);
	}
	
	private void checkInput(KeyEvent e){
		input = getText();
		if (	!(e.getKeyChar() == KeyEvent.VK_0)
				 && !(e.getKeyChar() == KeyEvent.VK_1)
				 && !(e.getKeyChar() == KeyEvent.VK_2)
				 && !(e.getKeyChar() == KeyEvent.VK_3)
				 && !(e.getKeyChar() == KeyEvent.VK_4)
				 && !(e.getKeyChar() == KeyEvent.VK_5)
				 && !(e.getKeyChar() == KeyEvent.VK_6)
				 && !(e.getKeyChar() == KeyEvent.VK_7)
				 && !(e.getKeyChar() == KeyEvent.VK_8)
				 && !(e.getKeyChar() == KeyEvent.VK_9) 
				 && !(e.getKeyChar() == KeyEvent.VK_TAB)
				 && !(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
				 && !(e.getKeyCode() == KeyEvent.VK_RIGHT)
				 && !(e.getKeyCode() == KeyEvent.VK_LEFT)
				 && !(e.getKeyCode() == KeyEvent.VK_UP)
				 && !(e.getKeyCode() == KeyEvent.VK_DOWN)) {
			System.err.println("[IntelligentTextfield] Char: " + e.getKeyChar() + 
					   " Code: " + e.getKeyCode() + " was not a number.");
				this.setText(getText().replaceAll("\\D", ""));
		} 
	}

}
