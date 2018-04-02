/**
 * 
 */
package clientSide.gui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;

/**
 * @author keenangaudio
 *
 */
public class UserView extends JFrame {

	JPanel mainView;
	JPanel[] viewPanels;	
	JMenu menu;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public UserView(String arg0) throws HeadlessException {
		super(arg0);
		
	}
	
	public void login() {
		
	}
}
