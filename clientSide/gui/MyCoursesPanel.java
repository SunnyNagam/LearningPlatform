/**
 * 
 */
package clientSide.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class MyCoursesPanel extends JPanel {
	private JButton createNew;
	
	public MyCoursesPanel() {
		
	}
	public void profTools() {
		createNew = new JButton("Create new Course");
		add(createNew, BorderLayout.SOUTH);
	}
}
