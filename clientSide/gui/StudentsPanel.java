/**
 * 
 */
package clientSide.gui;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import format.Course;
import clientSide.User;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class StudentsPanel extends JPanel {
	private JButton enrolB;
	public DefaultListModel<User> students;
	private JList<User> studentsList;
	
	//private JPanel display;
	
	public StudentsPanel() {
		students = new DefaultListModel<User>();
		
		studentsList = new JList<User> (students);
	}
	
	public void update() {
		studentsList.updateUI();
	}
	
	public void refreshData(ArrayList<User> set) {
		try {
			students.clear();
			for(int x=0; x< set.size(); x++) {
				students.addElement(set.get(x));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}
}
