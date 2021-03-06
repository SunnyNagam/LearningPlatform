/**
 * 
 */
package clientSide.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;

import format.*;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class MyCoursesPanel extends JPanel {
	private JButton createNew, toggleB;
	public DefaultListModel<Course> myCourses;
	public JList<Course> courseList;
	private boolean admin;
	
	//private JPanel display;
	
	public MyCoursesPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		myCourses = new DefaultListModel<Course>();
		
		courseList = new JList<Course> (myCourses);
		courseList.setFont(new Font( "menlo",Font.PLAIN,12) );//520/140
		
	}
	
	/**
	 * Updates ui
	 */
	public void update() {
		courseList.updateUI();
	}
	
	/**
	 * initalizes courses view for students
	 * @param a an actiaonlisteer
	 * @param b another actionlistener
	 */
	public void studTools(ActionListener a, ActionListener b) {
		admin = false;
		add( setup() );
		System.err.println("Creating studTools.");
		createNew = new JButton( "Enter Chatroom" );
		createNew.addActionListener(a);
		//add( createNew, BorderLayout.SOUTH );
		
		toggleB = new JButton( "View Grades" );
		toggleB.addActionListener(b);
		add( toggleB, BorderLayout.SOUTH );
	}
	
	/**
	 * initalizes courses view for professors
	 * @param a an actiaonlisteer
	 * @param b another actionlistener
	 */
	public void profTools(ActionListener a, ActionListener b) {
		admin = true;
		add( setup() );
		System.err.println("Creating profTools.");
		createNew = new JButton( "Create new Course" );
		createNew.addActionListener(a);
		add( createNew, BorderLayout.SOUTH );
		
		toggleB = new JButton( "Toggle Active" );
		toggleB.addActionListener(b);
		add( toggleB, BorderLayout.SOUTH );
		//(new Exception()).printStackTrace();
	}
	
	/**
	 * refreshes data
	 * @param set data to use
	 */
	public void refreshData(ArrayList<Course> set) {
		System.err.println("Refreshing course data in gui");
		try {
			myCourses.removeAllElements();
			for(int x = 0; x < set.size(); x++) {
				if(admin || set.get(x).isActive() ) myCourses.addElement(set.get(x));
				System.err.println("Adding: " + set.get(x));
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
		x.setPreferredSize(new Dimension(520,150));
		tmp.add(x);
		return tmp;
	}

	

}
