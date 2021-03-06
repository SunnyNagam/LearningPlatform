/**
 * 
 */
package clientSide.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
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
import clientSide.Controller;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class StudentsPanel extends JPanel {
	private JButton enrolB;
	public DefaultListModel<User> students;
	private JList<User> studentsList;
	public JLabel title;
	public SearchPanel searchPanel;
	
	//private JPanel display;
	/**
	 * initialized this panel
	 */
	public StudentsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		students = new DefaultListModel<User>();
		
		studentsList = new JList<User> (students);
		studentsList.setFont(new Font("menlo",Font.PLAIN,12));
		
		JPanel tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
		title = new JLabel("");
		tmp.add(title);
		JScrollPane x = new JScrollPane(studentsList);
		tmp.add(x);
		searchPanel = new SearchPanel();
		add (tmp);
		add ( searchPanel );
		add (enrollButton());
	}
	
	private JPanel enrollButton() {
		JPanel x = new JPanel();
		x.setLayout(new BoxLayout(x, BoxLayout.X_AXIS));
		x.add(Box.createHorizontalGlue());
		x.add(enrolB = new JButton("Toggle Enrollment"));
		return x;
	}
	/**
	 * adds action listener to enroll button (the only internal button )
	 * @param a
	 */
	public void addListen(ActionListener a) {
		enrolB.addActionListener(a);
	}
	/**
	 * updates UI
	 */
	public void update() {
		studentsList.updateUI();
	}
	/**
	 * refreshes data in internal JLists
	 * @param set the thingy to send
	 * @param c the controller idk
	 */
	public void refreshData(ArrayList<String[]> set, Controller c) {
		//if (set == null) return;
		System.err.println("Refreshing student data in gui");
		try {
			students.removeAllElements();
			for(int x=0; x< set.size(); x++) {
				String[] arr = set.get(x);
				User stud = new Student(Integer.parseInt(arr[0]),arr[1],arr[2],arr[3], Integer.parseInt(arr[4]));
				System.err.println("about to check if in selected");
				stud.currentCourse(c.client.inSelectedCourse(stud, c.selectedCourse));
				students.addElement(stud);
				System.err.println("Adding: "+students.get(x)+", ID: "+set.get(x)[4]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}
	/**
	 * @return the selected student in the panel
	 */
	public Student getSelectedStudent() {
		return (Student) studentsList.getSelectedValue();
		
	}
}
