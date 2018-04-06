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

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class StudentsPanel extends JPanel {
	private JButton enrolB;
	public DefaultListModel<Course> students;
	private JList<Course> studentsList;
	
	//private JPanel display;
	
	public StudentsPanel() {
		students = new DefaultListModel<Course>();
		
		studentsList = new JList<Course> (students);
	}
	
	public void update() {
		studentsList.updateUI();
	}
	
	public void refreshData(ArrayList<Course> set) {
		try {
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
