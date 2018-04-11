package clientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clientSide.gui.*;
import format.Assignment;
import format.Course;

public class Student extends User {

	public Student() {
		
	}
	public Student(int type, String first, String last, String email, int id) {
		this.userType = type;
		this.firstName = first;
		this.lastName = last;
		this.email = email;
		this.id = id;
	}
	@Override
	JPanel[] instantiatePanels() {
		JPanel[] tmp = new JPanel[PanelList.ARRAY_SIZE]; 
		tmp[MY_COURSES] = createMyCourses();
		tmp[COURSE] = createCoursePanel();
		tmp[ASSIGNMENTS] = createAssignmentsPanel();
		tmp[GRADES] = createGradesPanel();
		tmp[DROPBOX]	 = createDropBox();
		//the rest aren't needed yet
		return tmp;
	}
	
	private JPanel createDropBox() {
		JPanel tmp = new DropboxPanel();

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
	
	@Override
	void assignButtons(Controller c) {
		System.err.println("assigning buttons");

		// Create new course button
		((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).profTools(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.err.println("prof tool butt assigned");
				// create a course
				String cName = JOptionPane.showInputDialog("Enter the new course name: ");
				c.client.addCourse(-1, cName, String.valueOf(getId()), false);
				ArrayList<Course> set = c.client.getCourses(c.user.id);
				((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}

		},

				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.err.println("toggle butt setup");
						// create a course
						c.client.toggleCourse(c.selectedCourse);
						ArrayList<Course> set = c.client.getCourses(c.user.id);
						((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
					}

				});

		// Course menu botton
		c.gui.getMenu()[PanelList.MY_COURSES].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Courses menu action started");
				ArrayList<Course> set = c.client.getCourses(c.user.id);
				System.err.println("Got responce set from db. " + set.size());
				((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}
		});

		// getting selected course from course panel
		((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).courseList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).courseList.isSelectionEmpty()){
					return;
				}
				Course n = ((Course) ((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).courseList
						.getSelectedValue());
				c.selectedCourse = n.getCourseID();
				c.selectedName = n.getCourseName();
				c.gui.selectedCourse.setText(
						c.selectedCourse == -1 ? "Select A Course" : String.valueOf("Selected: " + c.selectedName));
				System.err.println("Course selected = " + c.selectedCourse);
			}
		});

		/*/ add functionaliity to ENROLL button in students panel
		((GradesPanel) c.gui.getPanels()[PanelList.GRADES]).addListen(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Students enroll action started");
				Student stu;
				if ((stu = ((StudentsPanel) c.gui.getPanels()[PanelList.STUDENTS]).getSelectedStudent()) != null) {
					// stu.currentCourse(c.selectedCourse);
					c.client.toggleEnroll(stu, c.selectedCourse);
				}

				ArrayList<String[]> set = c.client.getStudents(c.user.id);
				System.err.println("Got responce set from db. " + set.size());
				((StudentsPanel) c.gui.getPanels()[PanelList.STUDENTS]).refreshData(set, c);
			}
		});*/
		StudentAssignmentPanel pa = ((StudentAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]);

		// add functionaliity to button in profassign (UPLOAD)
		pa.addListen(
				
		// add functionaliity to button in profassign (DROPBOX)
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("View Dropbox action started");
				
				//pa.dbView = new DropboxPanel();
				c.gui.switchWindow(PanelList.DROPBOX);
				pa.dbView.setDbox(c.client.getDropbox(pa.assignmnetsList.getSelectedValue().id));
				pa.dbView.init();
				pa.dbView.setVisible(true);
			}
		});

		// assignments menu botton
		c.gui.getMenu()[PanelList.ASSIGNMENTS].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Assignment menu action started");

				ArrayList<Assignment> set = c.client.getAssignments(c.selectedCourse);
				((StudentAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).refreshData(set);
			}
		});
		
		/*/searchPanel stuff
		SearchPanel s = ((StudentsPanel) c.gui.getPanels()[PanelList.STUDENTS]).searchPanel;
		s.searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { //search button
				try {
					String sKey = s.getKey();
					ArrayList<String[]> set = new ArrayList<String[]>();
					if (s.by().equals(SearchPanel.ID_STRING)) {
						set =  c.client.searchId(Integer.parseInt(s.getKey())) ;
					} else if (s.by().equals(SearchPanel.NAME_STRING)) {
						set =  c.client.searchNm(sKey) ;
					} else throw new Exception("Something is VERY wrong.\nPlease restart the program.");
					((StudentsPanel) c.gui.getPanels()[PanelList.STUDENTS]).refreshData(set, c);
				} catch (Exception ex) { c.gui.displayErrorMessage( ex.getMessage() ); }
				
			}	
		});*/
	}


	
	
}
