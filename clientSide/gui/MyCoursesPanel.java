/**
 * 
 */
package clientSide.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import format.*;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class MyCoursesPanel extends JPanel {
	private JButton createNew;
	public DefaultListModel<Course> myCourses;
	private JList<Course> courseList;
	
	//private JPanel display;
	
	public MyCoursesPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		myCourses = new DefaultListModel<Course>();
		
		courseList = new JList<Course> (myCourses);
		courseList.setFont(new Font("menlo",Font.PLAIN,12));
	}
	
	public void update() {
		courseList.updateUI();
	}
	
	public void StudTools() {
		add( setup() );
		//maybe add grades here
	}
	public void profTools(ActionListener a) {
		add( setup() );
		System.err.println("Creating profTools.");
		createNew = new JButton( "Create new Course" );
		createNew.addActionListener(a);
		add( createNew, BorderLayout.SOUTH );
	}
	
	public void refreshData(ArrayList<Course> set) {
		System.err.println("Refreshing course data in gui");
		try {
			myCourses.removeAllElements();
			for(int x = 0; x < set.size(); x++) {
				System.err.println("Adding course: "+set.get(x).getCourseID());
				myCourses.addElement(set.get(x));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}
	private JPanel setup() {
		JPanel tmp = new JPanel();
		tmp.add(new JLabel("Course ID // Course Name // Active "));
		JScrollPane x = new JScrollPane(courseList);
		//x.setPreferredSize(new Dimension(300,200));
		tmp.add(x);
		return tmp;
	}
	

}
