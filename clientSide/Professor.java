package clientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clientSide.gui.*;
import format.Course;

class Professor extends User{

	public Professor() {
		
	}
	
	@Override
	JPanel[] instantiatePanels() {
		JPanel[] tmp = new JPanel[PanelList.ARRAY_SIZE]; 
		tmp[MY_COURSES] = createMyCourses();
		tmp[COURSE] 	= createCoursePanel();
		tmp[ASSIGNMENTS]= createAssignmentsPanel();
		tmp[STUDENTS] 	= createStudentsPanel();
		//the rest aren't needed yet
		return tmp;
	}

	private JPanel createStudentsPanel() {
		JPanel tmp = new StudentsPanel();
		
		return tmp;
	}
	private JPanel createAssignmentsPanel() {
		JPanel tmp = new ProfAssignmentPanel();
		
		return tmp;
	}
	private JPanel createCoursePanel() {
		JPanel tmp = new ProfCoursePanel();
		
		return tmp;
	}
	private JPanel createMyCourses() {
		JPanel tmp = new MyCoursesPanel();
		//include the 'create course' button
		return tmp;
	}

	@Override
	void assignButtons(Controller c) {
		System.err.println("assigning buttons");
		
		// Create new course button 
		((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).profTools(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.err.println("prof tool butt assigned");
				// create a course 
				String cName = JOptionPane.showInputDialog("Enter the new course name: ");
				c.client.addCourse(c.user.id, cName, false);
				ArrayList<Course> set = c.client.getCourses(c.user.id);
				((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}
			
		});
		
		//  Course menu botton
		c.gui.getMenu()[PanelList.MY_COURSES].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Courses menu action started");
				ArrayList<Course>  set = c.client.getCourses(c.user.id);
				System.err.println("Got responce set from db.");
				((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}
		});
		
		//  students menu botton
			c.gui.getMenu()[PanelList.STUDENTS].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.err.println("Students menu action started");
					ArrayList<User>  set = c.client.getStudents(c.user.id);
					System.err.println("Got responce set from db.");
					((StudentsPanel)c.gui.getPanels()[PanelList.STUDENTS]).refreshData(set);
				}
			});
	}
	
}
