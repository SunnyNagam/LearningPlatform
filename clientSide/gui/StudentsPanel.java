/**
 * 
 */
package clientSide.gui;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import format.Course;
import clientSide.Student;
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
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		students = new DefaultListModel<User>();
		
		studentsList = new JList<User> (students);
		studentsList.setFont(new Font("menlo",Font.PLAIN,12));
		
		JPanel tmp = new JPanel();
		tmp.add(new JLabel("User ID // First Name // Last Name "));
		JScrollPane x = new JScrollPane(studentsList);
		//x.setPreferredSize(new Dimension(300,200));
		tmp.add(x);
		
		add(tmp);
	}
	
	public void update() {
		studentsList.updateUI();
	}
	
	public void refreshData(ArrayList<String[]> set) {
		System.err.println("Refreshing student data in gui");
		try {
			students.removeAllElements();
			for(int x=0; x< set.size(); x++) {
				String[] arr = set.get(x);
				User stud = new Student(Integer.parseInt(arr[0]),arr[1],arr[2],arr[3], Integer.parseInt(arr[4]));
				students.addElement(stud);
				System.err.println("Adding: "+students.get(x)+", ID: "+set.get(x)[4]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}
}
