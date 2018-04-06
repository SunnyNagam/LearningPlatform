package clientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clientSide.gui.*;

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
		((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).profTools(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.err.println("prof tool butt assigned");
				// create a course 
				String cName = JOptionPane.showInputDialog("Enter the new course name: ");
				c.client.addCourse(c.user.id, cName, false);
				ResultSet set = c.client.getCourses(c.user.id);
				((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}
			
		});
		c.gui.getMenu()[PanelList.MY_COURSES].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Heyooo");
				ResultSet set = c.client.getCourses(c.user.id);
				System.err.println("Got responce set from db.");
				((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}
		});
	}
	
}
