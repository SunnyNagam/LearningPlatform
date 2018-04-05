package clientSide;

import javax.swing.JPanel;

import clientSide.gui.*;

public class Student extends User {

	public Student() {
		
	}
	public Student(String type, String first, String last, String email, int id) {
		this.userType = type;
		this.firstName = first;
		this.lastName = last;
		this.email = email;
		this.id = id;
	}
	@Override
	void assignButtons(Controller c) {
		// TODO Auto-generated method stub
		
	}
	@Override
	JPanel[] instantiatePanels() {
		JPanel[] tmp = new JPanel[PanelList.ARRAY_SIZE]; 
		tmp[MY_COURSES] = createMyCourses();
		tmp[COURSE] = createCoursePanel();
		tmp[ASSIGNMENTS] = createAssignmentsPanel();
		tmp[GRADES] = createGradesPanel();
		//the rest aren't needed yet
		return tmp;
	}

	private JPanel createGradesPanel() {
		JPanel tmp = new GradesPanel();
		
		return tmp;
	}
	private JPanel createAssignmentsPanel() {
		JPanel tmp = new StudentAssignmentPanel();
		
		return tmp;
	}
	private JPanel createCoursePanel() {
		JPanel tmp = new StudentCoursePanel();
		
		return tmp;
	}
	private JPanel createMyCourses() {
		JPanel tmp = new MyCoursesPanel();
		//do not include the 'create course' button
		return tmp;
	}
	
	
}
