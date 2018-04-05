package clientSide;

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
		// TODO Auto-generated method stub
		
	}
	
}
