/**
 * 
 */
package serverSide;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sound.midi.SysexMessage;

import format.Assignment;
import format.Communicate;
import format.Course;
import format.DropBox;
import format.Submission;

/**
 * @author keenangaudio
 *
 */
class Instance implements Runnable {
	ObjectInputStream in;
	ObjectOutputStream out;
	int clientType;
	DBHandler helper;

	Instance(InputStream in_, OutputStream out_/* , modelhandler */) {
		clientType = 0; // not a valid type
		try {
			// write connected
			out = new ObjectOutputStream(out_);
			// in = new ObjectInputStream(in_);
			out.writeInt(Communicate.CONNECTED);
			out.flush();

			System.out.println("Got wrote mate");

			// wait for response
			in = new ObjectInputStream(in_);
			System.out.println("Got input stream, waiting for input int");
			// check if response is what we're expecting
			if (in.readInt() != Communicate.CONNECTED)
				throw new IOException("Unexpected response from Client.");
			System.out.println("Connection to client established.\n" + "server instance running on "
					+ Thread.currentThread().getName());

			helper = new DBHelper();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		// set up model handling here
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			while (true) {
				int tag = in.readInt();
				System.err.println("Got command: " + tag);
				try {
					helper.start();
					parseTag(tag);
					helper.end();
					helper.refresh();
				} catch (SQLException e) { e.printStackTrace(); }
			}
		} catch (EOFException e) {
			System.err.println("Client Disconnected.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseTag(int tag) throws IOException {
		switch (tag) {
		case Communicate.LOGIN:
			login();
			break;
		case Communicate.GET:
			get();
			break;
		case Communicate.SEARCH_STRING:
			searchNm();
			break;
		case Communicate.SEARCH_NAME:
			searchId();
			break;
		case Communicate.TOGGLECOURSE:
			refresh();
			break;
		case Communicate.SYNC:
			sync();
			break;
		case Communicate.ENROLL:
			enroll();
			break;
		case Communicate.ENROLLED:
			enrolled();
			break;
		case Communicate.ASSIGNMENT:
			toggleAssignment();
			break;
		case Communicate.DISCONNECT:
			disconnect();
			break;
		}
	}

	private void searchId() {
		try {
			int key = in.readInt();
			System.err.println("getting students from db");
			ResultSet r = helper.search(Communicate.STUDENT, "ID", key);
			System.err.println("writing students from db");

			out.writeObject(parseRRUser(r));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.writeObject(null);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void searchNm() {
		try {
			String key = in.readUTF();
			System.err.println("getting students from db");
			ResultSet r = helper.search(Communicate.STUDENT, "LAST_NAME", key);
			System.err.println("writing students from db");

			out.writeObject(parseRRUser(r));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.writeObject(null);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void toggleAssignment() {
		int key;
		try {
			key = in.readInt();
			helper.toggleAssActive(key);

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void login() {
		try {
			// read username
			int username = in.readInt();
			// read password
			String password = in.readUTF();
			// pass to modelhandler - get type or write Communicate.DB_ERROR

			ResultSet set = helper.search(0, "ID", String.format("%d", username));
			if (!set.next()) {
				out.writeInt(Communicate.INVALID);
				System.out.println("Null set returned.");
			} else {
				set.beforeFirst();
				set.next();
				if (password.equals(set.getString("PASSWORD"))) {
					out.writeInt(clientType = set.getInt("TYPE"));
				} else {
					out.writeInt(Communicate.INVALID);
				}
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void enroll() {
		try {
			int studentID = in.readInt();
			System.err.println("got student: " + studentID);
			int courseID = in.readInt();
			System.err.println("got course: " + courseID);
			helper.toggleEnroll(studentID, courseID);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void enrolled() {
		try {
			int studentID = in.readInt();
			System.err.println("got student: " + studentID);
			int courseID = in.readInt();
			System.err.println("got course: " + courseID);
			ResultSet r = helper.enrolled(studentID, courseID);
			boolean b = (r != null) && r.first();
			System.err.println("enrolled: " + b);
			out.writeBoolean(b);
			out.flush();
			System.err.println("wrote^");

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void get() throws IOException {
		try {

			// read type && find object to send
			int type = in.readInt();
			if (checkDisconnect(in))
				disconnect();
			System.err.println("getting type " + type);
			get(type);

			// int status = (true) ? Communicate.DB_SUCCESS : Communicate.DB_ERROR;
			// out.writeInt(status);
			// write object (cases)

		} catch (IOException e) {
		}

	}

	private void get(int tag) {
		switch (tag) {
		case Communicate.STUDENT:
			getStudent();
			break;
		case Communicate.COURSE:
			getCourses();
			break;
		case Communicate.EMAIL:
			emailResponse();
			break;
		case Communicate.FILE:
			fileResponse();
			break;
		case Communicate.ASSIGNMENT:
			getAssignment();
			break;
		case Communicate.MESSAGE:
			messageResponse();
			break;
		case Communicate.SUBMISSION:
			getSubmissions();
			break;
		}
	}

	private void getSubmissions() {
		try {
			int assignID = in.readInt();

			System.err.println("getting submissions from db");
			ResultSet r = helper.submissions(assignID);
			System.err.println("writing submissions from db");
			//System.out.println(parseRRSubmission(r).getSubmissons().iterator());
			out.writeObject(parseRRSubmission(r));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.writeObject(null);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void getCourses() {
		try {
			int key = in.readInt();
			if (key == Communicate.NAME)
				getCourseName();

			System.err.println("getting courses from db");
			ResultSet r;
			if(clientType == Communicate.STUDENT)
				r = helper.getEnrolledCourses(key);
			else
				r = helper.search(Communicate.COURSE, "PROF_ID", key);
			System.err.println("writing courses from db");

			out.writeObject(parseRRCourse(r));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.writeObject(null);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void getCourseName() {
		// write(selectedCourse);
		try {
			int id = in.readInt();
			ResultSet r;
			if ((r = helper.search(Communicate.COURSE, "ID", id)).first())
				out.writeUTF(r.getString("NAME"));
			else
				out.writeUTF("");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getStudent() {
		try {
			System.err.println("getting students from db");
			ResultSet r = helper.search(Communicate.STUDENT, "TYPE", 8);
			System.err.println("writing students from db");

			out.writeObject(parseRRUser(r));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.writeObject(null);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void getAssignment() {
		try {
			int key = in.readInt();
			System.err.println("getting assognments from db");
			ResultSet r = helper.search(Communicate.ASSIGNMENT, "COURSE_ID", key);
			System.err.println("writing assignments from db");

			out.writeObject(parseRRAssignment(r));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.writeObject(null);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void messageResponse() {
		// read message

		// send message to model
	}

	private void emailResponse() {
		// read course

		// write prof email

	}

	private void fileResponse() {
		if (clientType == Communicate.STUDENT) {
			studentFile();
		} else { // client type == prof
			profFile();
		}

	}

	private void studentFile() {
		// read student

		// read course

		// read assignment

		// read file

		// give file to model
	}

	private void profFile() {
		// read course

		// read assignment

		// read file

		// give file to model
	}

	private void refresh() { // actually toggle active
		try {
			int id = in.readInt();
			helper.toggleActive(id);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sync() { // maybe unnecessary
		int type = 0;
		try {
			type = in.readInt();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (type == Communicate.ASSIGNMENT) {
			Assignment a;
			try {
				a = (Assignment) in.readObject();
				String path = helper.addAssignment(a);
				byte[] content = (byte[]) in.readObject();
				
				File newFile = new File(path);
				try {
					if (!newFile.exists())
						newFile.createNewFile();
					FileOutputStream writer = new FileOutputStream(newFile);
					BufferedOutputStream bos = new BufferedOutputStream(writer);
					bos.write(content);
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (type == Communicate.COURSE) {
			Course x;
			try {
				x = (Course) in.readObject();
				helper.addCourse(x);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (type == Communicate.GRADES) {
			getGrade();
		}
	}
//	String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "ASSIGN_ID INT(8) NOT NULL, "
//			+ "STUDENT_ID INT(8) NOT NULL, " + "COURSE_ID INT(8) NOT NULL, " + "ASSIGNMENT_GRADE INT(3), "
//			+ "PRIMARY KEY ( id ))";
	private void getGrade() {
		int subID = -1, courseID = -1, grade = 0, assignID = -1, studID = -1, maxGrade = 0, g;
		try {
			subID = in.readInt();
			courseID = in.readInt();
			grade = maxGrade = in.readInt();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// add new grade
		helper.update(Communicate.SUBMISSION,"SUBMISSION_GRADE", grade, subID);
		// get assign id student id and course id
		ResultSet r;
		try {
			(r = helper.search(Communicate.SUBMISSION, "ID", subID)).first();
			assignID = r.getInt("ASSIGN_ID");
			studID = r.getInt("STUDENT_ID");
		// update best grade for assignment 
			r = helper.searchf(Communicate.SUBMISSION, "ASSIGN_ID=? AND STUDENT_ID=?", assignID, studID);
			while ( r.next() ) 
				if ((g = r.getInt("SUBMISSION_GRADE")) > maxGrade) maxGrade = g;

			if ( (r = helper.searchf(Communicate.GRADES, 
					"ASSIGN_ID=? AND STUDENT_ID=? AND COURSE_ID=?", assignID, studID, courseID)).first() )
				helper.update(Communicate.GRADES, "ASSIGNMENT_GRADE", maxGrade, r.getInt("ID"));
			else {
				helper.addGrade(maxGrade, assignID, studID, courseID);
			}
		} catch (Exception e) { e.printStackTrace(); return;}
	}
	private void disconnect() throws IOException {
		// write disconnect back

		// close everything

		throw new IOException(
				"Client requested disconnect.\n" + "Shutting down on " + Thread.currentThread().getName());
	}

	/**
	 * use whenever reading an input, checks for unexpected client disconnect
	 * 
	 * @param input
	 *            the object being read
	 * @return true if disconnect was received
	 */
	private boolean checkDisconnect(Object input) {
		int check = 0;
		try {
			check = (int) input;
		} catch (ClassCastException e) {
		}
		return (check == Communicate.DISCONNECT);
	}

	private DropBox parseRRSubmission(ResultSet r) {
		ArrayList<Submission> arr = new ArrayList<Submission>();
		try {
			while (r.next()) {
				arr.add(Submission.castRR(r));
			}
			System.err.println("Elements in set: " + arr.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DropBox db = new DropBox(arr);
		return db;
	}
	private ArrayList<Course> parseRRCourse(ResultSet r) {
		ArrayList<Course> arr = new ArrayList<Course>();
		try {
			while (r.next()) {
				arr.add(Course.castRR(r));
			}
			System.err.println("Elements in set: " + arr.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	private ArrayList<String[]> parseRRUser(ResultSet r) {
		ArrayList<String[]> arr = new ArrayList<String[]>();
		try {
			while (r.next()) {
				// nt(int type, String first, String last, String email, int id)
				arr.add(new String[] { String.valueOf(r.getInt("TYPE")), r.getString("FIRST_NAME"),
						r.getString("LAST_NAME"), r.getString("EMAIL"), String.valueOf(r.getInt("ID")) });
			}
			System.err.println("Elements in set: " + arr.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	private ArrayList<Assignment> parseRRAssignment(ResultSet r) {
		ArrayList<Assignment> arr = new ArrayList<Assignment>();
		try {
			while (r.next()) {
				// nt(int type, String first, String last, String email, int id)
				arr.add(new Assignment(r.getString("TITLE"), r.getString("PATH"), r.getBoolean("ACTIVE"),
						r.getInt("COURSE_ID"), r.getInt("ID"), r.getString("DUE_DATE")));
			}
			System.err.println("Elements in set: " + arr.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

}
