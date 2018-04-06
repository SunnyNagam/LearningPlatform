/**
 * 
 */
package clientSide.gui;

import java.awt.BorderLayout;
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
		myCourses = new DefaultListModel<Course>();
		
		courseList = new JList<Course> (myCourses);
	}
	
	public void update() {
		courseList.updateUI();
	}
	
	public void StudTools() {
		add( setupStud() );
		//maybe add grades here
	}
	public void profTools(ActionListener a) {
		add( setupProf() );
		
		createNew = new JButton( "Create new Course" );
		createNew.addActionListener(a);
		add( createNew, BorderLayout.SOUTH );
	}
	
	public void refreshData(ArrayList<Course> set) {
		try {
			for(int x = 0; x < set.size(); x++) {
				myCourses.addElement(set.get(x));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}
	private JPanel setupStud() {
		JPanel tmp = new JPanel();
		tmp.add(new JLabel("Course ID // Course Name // Prof ID "));
		tmp.add(courseList);
		return tmp;
	}
	private JPanel setupProf() {
		JPanel tmp = new JPanel();
		tmp.add(new JLabel("Course ID // Course Name // Active "));
		tmp.add(courseList);
		return tmp;
	}
	

}
