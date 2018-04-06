package clientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clientSide.gui.*;
import format.Assignment;
import format.Course;

class Professor extends User {
	private String first = "User ID // First Name // Last Name // Enrolled in ";

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
		tmp[COURSE] = createCoursePanel();
		tmp[ASSIGNMENTS] = createAssignmentsPanel();
		tmp[STUDENTS] = createStudentsPanel();
		// the rest aren't needed yet
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
		// include the 'create course' button
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

		// getting selected course
		((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).courseList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Course n = ((Course) ((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).courseList
						.getSelectedValue());
				c.selectedCourse = n.getCourseID();
				c.selectedName = n.getCourseName();
				c.gui.selectedCourse.setText(
						c.selectedCourse == -1 ? "Select A Course" : String.valueOf("Selected: " + c.selectedName));
				System.err.println("Course selected = " + c.selectedCourse);
			}
		});

		// add functionaliity to ENROLL button in students panel
		((StudentsPanel) c.gui.getPanels()[PanelList.STUDENTS]).addListen(new ActionListener() {
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
		});
		uploadAssign(c);
		// add functionaliity to button in profassign
		((ProfAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).addListen(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Upload assign action started");
				// TODO UPLOAD
				((ProfAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).uploadPanel.setVisible(true);
			}
		},

				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.err.println("Toggle assign action started");
						Assignment temp = ((ProfAssignmentPanel) c.gui
								.getPanels()[PanelList.ASSIGNMENTS]).assignmnetsList.getSelectedValue();
						if (temp == null)
							return;
						c.client.toggleAssignment(temp.id);
						ArrayList<Assignment> set = c.client.getAssignments(c.selectedCourse);
						((ProfAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).refreshData(set);
					}
				});

		// students menu botton
		c.gui.getMenu()[PanelList.STUDENTS].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((StudentsPanel) c.gui.getPanels()[PanelList.STUDENTS]).title.setText(first + c.selectedName);
				System.err.println("Students menu action started");
				ArrayList<String[]> set = c.client.getStudents(c.user.id);
				System.err.println("Got responce set from db. " + set.size());
				((StudentsPanel) c.gui.getPanels()[PanelList.STUDENTS]).refreshData(set, c);
			}
		});

		// assignments menu botton
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
		InsertView f = ((ProfAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).uploadPanel;

		f.insertB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File selectedFile = new File(f.pathtxt.getText());
				System.err.println("Looking for file: " + f.pathtxt.getText());
				if (!selectedFile.exists()) {
					c.gui.displayErrorMessage("Invalid file path!");
					return;
				}
				long length = selectedFile.length();
				byte[] content = new byte[(int) length];
				try {
					FileInputStream fis = new FileInputStream(selectedFile);
					BufferedInputStream bos = new BufferedInputStream(fis);
					bos.read(content, 0, (int) length);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.err.println("uploading assignment now:) from "+f.pathtxt.getText());
				c.client.upload(f.titletxt.getText(), f.pathtxt.getText(), f.duetxt.getText(), f.activeBox.isSelected(),
						c.selectedCourse, content);
				System.err.println("done uploading!");
				f.setVisible(false);
				f.clearInput();
				
				ArrayList<Assignment> set = c.client.getAssignments(c.selectedCourse);
				((ProfAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).refreshData(set);
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
