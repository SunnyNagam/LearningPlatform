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
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clientSide.gui.*;
import format.Assignment;
import format.Course;
import format.DropBox;

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
		tmp[DROPBOX] = createDropBox();
		tmp[EMAIL_MAKER] = createEmailMaker();
		// the rest aren't needed yet
		return tmp;
	}

	private JPanel createEmailMaker() {
		JPanel tmp = new ComposeEmailPanel();
		
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
		// do not include the 'create course' button
		return tmp;
	}

	@Override
	void assignButtons(Controller c) {
		System.err.println("assigning buttons");
		//EMAIL
		assignEmail(c);
		// Create new course button
		((MyCoursesPanel) c.gui.getPanels()[PanelList.MY_COURSES]).studTools(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.err.println("stud tool butt assigned");
				//TODO goto chat room maybe
			}
		},
		//toggle button
			new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.err.println("toggle butt setup");
				// create a course
				if(c.selectedCourse == -1) {
					return;
				}
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

		// add functionaliity to button in studentassign (UPLOAD)
		pa.addListen(
				
		// add functionaliity to button in profassign (DROPBOX)
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("View Dropbox action started");
				
				pa.dbView = new DropboxPanel();

				c.gui.switchWindow(PanelList.DROPBOX);
				if(pa.assignmnetsList.isSelectionEmpty()) {
					c.gui.switchWindow(PanelList.ASSIGNMENTS);
					return;
				}
				//MY JUNK
				DropBox db = c.client.getDropbox(pa.assignmnetsList.getSelectedValue().id, id);
				//pa.dbView.setDbox(db);
				//pa.dbView.init();
				((DropboxPanel)c.gui.getPanels()[PanelList.DROPBOX]).refreshData(db, c);
				
			}
		},
		
		// SUBMIT SUBMISSION
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("SUBMIT TO Dropbox action started");
				((StudentAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).uploadPanel.setVisible(true);
				
			}
		},	
		// Download SUBMISSION
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						System.err.println("Download Assignment file action started");
						
						
						if(pa.assignmnetsList.isSelectionEmpty()) {
							return;
						}
						c.client.downloadAssignment(pa.assignmnetsList.getSelectedValue().id);
						
					}
				}		);

		// assignments menu botton
		c.gui.getMenu()[PanelList.ASSIGNMENTS].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Assignment menu action started");
				if(c.selectedCourse == -1) {
					return;
				}
				ArrayList<Assignment> set = c.client.getAssignments(c.selectedCourse);
				((StudentAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).refreshData(set);
			}
		});
		
		InsertView f = ((StudentAssignmentPanel) c.gui.getPanels()[PanelList.ASSIGNMENTS]).uploadPanel;
		
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
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.err.println("uploading sub now:) from " + f.pathtxt.getText());

			/// change dis
			c.client.uploadSub(f.titletxt.getText(), f.pathtxt.getText(), f.duetxt.getText(), 
					((StudentAssignmentPanel)c.gui.getPanels()[PanelList.ASSIGNMENTS]).assignmnetsList.getSelectedValue().id, 
					id, 
					((StudentAssignmentPanel)c.gui.getPanels()[PanelList.ASSIGNMENTS]).assignmnetsList.getSelectedValue().courseID, 
					content);

			System.err.println("done uploading!");
			f.setVisible(false);
			f.clearInput();

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

	private void assignEmail(Controller c) {
		
		//set default text
		c.gui.getMenu()[PanelList.EMAIL_MAKER].addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(c.selectedCourse == -1) {
					return;
				}
				ComposeEmailPanel em = ((ComposeEmailPanel)c.gui.getPanels()[PanelList.EMAIL_MAKER]);
				System.err.println("Opened email, getting to:");
				String to = c.client.getEmails(c.selectedCourse);
				System.err.println("sending to: " + to);
				em.text[0].setText( to.split(", ")[0] );		//to (first index is prof email)
				em.text[1].setText( c.client.getEmail(id) );									//from
			}
		});
		
		//send button
		((ComposeEmailPanel)c.gui.getPanels()[PanelList.EMAIL_MAKER]).assignListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.err.println("sending email.");
				ComposeEmailPanel em = ((ComposeEmailPanel)c.gui.getPanels()[PanelList.EMAIL_MAKER]);
				String pass = c.getPassword();
				//System.err.println("passing to email helper: " + em.getFrom() + " " + c.user.firstName + " " + c.user.lastName + " to: " +Arrays.toString(em.getTo().split(", ")));
				if ( EmailKit.defineEmail(em.getFrom(), "Email from: " +c.user.firstName + " " + c.user.lastName , 
						em.getTo().split(", "), pass).sendFormatted(em.getSubj(),em.getBod()) )
					c.gui.displayErrorMessage("Email Sent!");
				else 
					c.gui.displayErrorMessage("Error Sending Email...");
			}
		});
		
	}

}
