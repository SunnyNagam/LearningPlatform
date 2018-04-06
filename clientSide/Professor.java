package clientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clientSide.gui.*;
import format.Assignment;
import format.Course;

class Professor extends User{

	public Professor() {
		
	}
	
	public Professor(int type, String first, String last, String email, int id) {
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
				c.client.addCourse(-1, cName,String.valueOf(getId()), false);
				ArrayList<Course> set = c.client.getCourses(c.user.id);
				((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}
			
		},
		
		new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.err.println("toggle butt setup");
				// create a course 
				c.client.toggleCourse(c.selectedCourse);
				ArrayList<Course> set = c.client.getCourses(c.user.id);
				((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}
			
		}
		);
		
		//  Course menu botton
		c.gui.getMenu()[PanelList.MY_COURSES].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Courses menu action started");
				ArrayList<Course>  set = c.client.getCourses(c.user.id);
				System.err.println("Got responce set from db. "+set.size());
				((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
			}
		});
		
		// getting selected course
		((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).courseList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				c.selectedCourse = ((Course) ((MyCoursesPanel)c.gui.getPanels()[PanelList.MY_COURSES]).courseList.getSelectedValue()).getCourseID();
				c.gui.selectedCourse.setText(c.selectedCourse==-1?"Select A Course":String.valueOf("Selected: "+c.selectedCourse));
				System.err.println("Course selected = "+c.selectedCourse);
			}
		});
		
		// add functionaliity to ENROLL button in students panel
		((StudentsPanel)c.gui.getPanels()[PanelList.STUDENTS]).addListen(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Students enroll action started");
				Student stu;
				if ((stu = ((StudentsPanel) c.gui.getPanels()[PanelList.STUDENTS]).getSelectedStudent()) != null) {
					//stu.currentCourse(c.selectedCourse);
					c.client.toggleEnroll(stu, c.selectedCourse);
				}
				
				ArrayList<String[]>  set = c.client.getStudents(c.user.id);
				System.err.println("Got responce set from db. "+set.size());
				((StudentsPanel)c.gui.getPanels()[PanelList.STUDENTS]).refreshData(set, c);
			}
		});
		uploadAssign(c);
		// add functionaliity to button in profassignment panel
				((ProfAssignmentPanel)c.gui.getPanels()[PanelList.ASSIGNMENTS]).addListen(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.err.println("Upload assign action started");
						//TODO UPLOAD
						((ProfAssignmentPanel)c.gui.getPanels()[PanelList.ASSIGNMENTS]).uploadPanel.setVisible(true);
						ArrayList<Assignment> set = c.client.getAssignments(c.selectedCourse);
						((ProfAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).refreshData(set);
					}
				});
		
		//  students menu botton
			c.gui.getMenu()[PanelList.STUDENTS].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.err.println("Students menu action started");
					ArrayList<String[]>  set = c.client.getStudents(c.user.id);
					System.err.println("Got responce set from db. "+set.size());
					((StudentsPanel)c.gui.getPanels()[PanelList.STUDENTS]).refreshData(set,c);
				}
			});
			
		//  assignments menu botton
					c.gui.getMenu()[PanelList.ASSIGNMENTS].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							System.err.println("Assignment menu action started");
							
							ArrayList<Assignment> set = c.client.getAssignments(c.selectedCourse);
							((ProfAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).refreshData(set);
						}
					});
	}

	private void uploadAssign(Controller c) {
		InsertView f = ((ProfAssignmentPanel)c.gui.getPanels()[PanelList.ASSIGNMENTS]).uploadPanel;
		
		f.insertB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// get text fields
				// send it to client to send to db
				// check if file is legit
				// print err message?
				// add to db
				f.setVisible(false);
				f.clearInput();
			}
			
		});
		
		f.returnB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.setVisible(false);
			}
			
		});
	}
	
}
