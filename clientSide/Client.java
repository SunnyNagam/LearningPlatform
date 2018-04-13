/**
 * 
 */
package clientSide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import clientSide.gui.UserView;
import format.Assignment;
import format.Communicate;
import format.Course;
import format.DropBox;
import format.Submission;

/**
 * @author keenangaudio
 *
 */
public class Client {

	boolean connected;
	Socket cSocket;
	private ObjectInputStream in;
	ObjectOutputStream out;

	Client(String name, String port) {
		connected = false;
		try {
			cSocket = new Socket(name, Integer.parseInt(port));

			out = new ObjectOutputStream(cSocket.getOutputStream());
			out.writeInt(Communicate.CONNECTED);
			out.flush();

			setIn(new ObjectInputStream(cSocket.getInputStream()));
			System.out.println("Got input stream, waiting for input int");
			connected = (getIn().readInt() == Communicate.CONNECTED);

			System.out.println("Client is " + (connected ? " " : "not ") + "connected.");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Sends attempted login to server
	 * @param userName to send
	 * @param password to send
	 * @return server login attempt responce
	 */
	public int attemptLogin(String userName, String password) {
		writeTag(Communicate.LOGIN);
		try {
			write(Integer.parseInt(userName));
		} catch (Exception ex) {
			System.err.println("yikes");
			write(Communicate.INVALID);
		}
		System.out.println("Almost yass");
		write(password);
		System.out.println("yass");
		return readInt();
	}

	/**
	 * Writes an integer representing a command tag to the server
	 * @param tag
	 */
	public void writeTag(int tag) {
		try {
			out.writeInt(tag);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes an int to the server
	 * @param toWrite int to write
	 */
	public void write(int toWrite) {
		try {
			out.writeInt(toWrite);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes an string to the server
	 * @param toWrite string to write
	 */
	public void write(String toWrite) {
		try {
			out.writeUTF(toWrite);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recives an int from the server
	 * @return integer read from the server
	 */
	public int readInt() {
		try {
			return in.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			return Communicate.INVALID;
		}
	}
	
	/**
	 * Recives an string from the server
	 * @return string to read from the server
	 */
	public String readString() {
		try {
			return in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Recives an bool from the server
	 * @return bool to read from the server
	 */
	public boolean readBoolean() {
		try {
			return in.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * What could this do?
	 */
	void communicate() {
		try {
			while (true) {
				throw new IOException();

			}
		} catch (IOException e) {
		}
	}

	/**
	 * gets the input stream
	 * @return input stream to return
	 */
	public ObjectInputStream getIn() {
		return in;
	}

	/**
	 * Sets the input strem
	 * @param in input stream to set to 
	 */
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	
	/**
	 * Retrieves courses for given user from server
	 * @param id of user to get courses from 
	 * @return arrayllist of courses 
	 */
	public ArrayList<Course> getCourses(int id) {
		writeTag(Communicate.GET);
		writeTag(Communicate.COURSE);
		write(id);
		try {
			Object o = in.readObject();
			System.err.println("Got from server: "+o);
			ArrayList<Course> c = (ArrayList<Course>) o;
			System.err.println("Client got " + c.size() + " courses from db");
			return c;
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * adds a new course to the database in server
	 * @param id id of course to ad
	 * @param cName name of course
	 * @param p_id prof id of course
	 * @param b active status of course
	 */
	public void addCourse(int id, String cName, String p_id, boolean b) {
		writeTag(Communicate.SYNC);
		writeTag(Communicate.COURSE);
		writeObject(new Course(id, cName, p_id, b));
	}

	/**
	 * writes object to server
	 * @param toWrite object to write
	 */
	private void writeObject(Object toWrite) {
		try {
			out.writeObject(toWrite);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gets students from server
	 * @param id of students to retrive 
	 * @return arraylist of string array of students to return 
	 */
	public ArrayList<String[]> getStudents(int id) {
		writeTag(Communicate.GET);
		writeTag(Communicate.STUDENT);
		try {
			return (ArrayList<String[]>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * toggles course activity 
	 * @param selectedCourse course to toggle active
	 */
	public void toggleCourse(int selectedCourse) {
		writeTag(Communicate.TOGGLECOURSE);
		write(selectedCourse);
	}

	/**
	 * gets assignmetns from server
	 * @param id of course of assignmetns to retrive from
	 * @return arraylist of string array of assignmetns to return 
	 */
	public ArrayList<Assignment> getAssignments(int selectedCourse) {
		writeTag(Communicate.GET);
		writeTag(Communicate.ASSIGNMENT);
		write(selectedCourse);
		try {
			return (ArrayList<Assignment>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * toggles enrolment of a course
	 * @param stu, student id to toggle enrolment 
	 * @param courseID, course to toggle student enrollmetn from 
	 */
	public void toggleEnroll(Student stu, int courseID) {
		writeTag(Communicate.ENROLL);
		write(stu.id);
		write(courseID);
	}

	/**
	 * returns if a user is in a course
	 * @param usr, user to check
	 * @param selectedCourse, course to check 
	 * @return true if user is in the course, false of user is not in the course
	 */
	public boolean inSelectedCourse(User usr,int selectedCourse) {
		writeTag(Communicate.ENROLLED);
		write(usr.id);
		write(selectedCourse);
		boolean t = readBoolean();
		System.err.println("CLIENT::student " + usr.id + " in course: " + t);
		return t;
	}

	/**
	 * gets the name of a course
	 * @param selectedCourse id of course to find the name of 
	 * @return name of the course
	 */
	public String getCourseName(int selectedCourse) {
		writeTag(Communicate.GET);
		writeTag(Communicate.COURSE);
		writeTag(Communicate.NAME);
		write(selectedCourse);
		String str = readString();
		str = (str==null)?"NULL":str;
		return str;
	}
	
	/**
	 * Retrives a dropbox from the server for the prof
	 * @param id of course to get dropbox from 
	 * @return dropbox to return
	 */
	public DropBox getDropbox(int id) {
		writeTag(Communicate.GET);
		writeTag(Communicate.SUBMISSION);
		write(id);
		
		try {
			return (DropBox) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retrives a dropbox from the server for the prof
	 * @param id of course to get dropbox from 
	 * @param student student id of dropbox
	 * @return dropbox to return
	 */
	public DropBox getDropbox(int id, int student) {
		writeTag(Communicate.GET);
		writeTag(Communicate.SUBMISSION);
		write(id);
		write(student);
		
		try {
			return (DropBox) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * toggles assignment activity
	 * @param ID of assignment to toggle
	 */
	public void toggleAssignment(int ID) {
		writeTag(Communicate.ASSIGNMENT);
		write(ID);
	}

	/**
	 * uploads a assignment to the server database
	 * @param title of assignment
	 * @param path file path of assignment
	 * @param due due date of assignmetn
	 * @param active active status of the assignment
	 * @param course course id of assignment
	 * @param file to upload
	 */
	public void upload(String title, String path, String due, boolean active, int course, byte[] file) {
		writeTag(Communicate.SYNC);
		writeTag(Communicate.ASSIGNMENT);
		String temp = path.substring(path.lastIndexOf("/")+1,path.length());
		System.err.println(temp);
		writeObject(new Assignment(title,temp,active,course,-1,due));	// assign but path is 'default' and id is -1
		writeObject(file);
	}
	
	/**
	 * uploads a submission to the server database
	 * @param title title of submission 
	 * @param path filepath of submission
	 * @param due duedate of submission
	 * @param assign assignment id of submissioln
	 * @param student student id of submission
	 * @param course couse id of submision 
	 * @param file file to submit
	 */
	public void uploadSub(String title, String path, String due, int assign, int student, int course, byte[] file) {
		writeTag(Communicate.SYNC);
		writeTag(Communicate.SUBMISSION);
		String temp = path.substring(path.lastIndexOf("/")+1,path.length());
		System.err.println(temp);
		writeObject(new Submission(-1,assign,student,course,temp,title,0,due));	// assign but path is 'default' and id is -1
		writeObject(file);
	}

	/**
	 * searches for key to in server database
	 * @param sKey  id to searc for
	 * @return returning resultset values 
	 */
	public ArrayList<String[]> searchId(int sKey) {
		writeTag(Communicate.SEARCH_NAME);
		write(sKey);
		try {
			return (ArrayList<String[]>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * searches for key to in server database
	 * @param sKey  name to searc for
	 * @return returning resultset values 
	 */
	public ArrayList<String[]> searchNm(String sKey) {
		writeTag(Communicate.SEARCH_STRING);
		write(sKey);
		try {
			return (ArrayList<String[]>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns list of strings containing assignment and correspoding grade
	 * for all active assignments for a specified student	
	 * @param student, id of student 
	 * @return ArrayList of strings containing the grades infromation
	 */
	public ArrayList<String> getGrades(int student) {
		writeTag(Communicate.GET);
		writeTag(Communicate.GRADES);
		write(student);
		
		try {
			return (ArrayList<String>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * submits grade of submission
	 * @param id is to grade
	 * @param selectedCourse couse o submission
	 * @param grade of submission to submit
	 */
	public void submitGrade(int id, int selectedCourse, int grade) {
		writeTag(Communicate.SYNC);
		writeTag(Communicate.GRADES);
		
		write(id);
		write(selectedCourse);
		write(grade);
	}

	/**
	 * gets the submission specified and downloads it to client machine
	 * @param id id of submission
	 * @return sucess of download process
	 */
	public boolean getSubDown(int id) {
		write(Communicate.GET);
		write(Communicate.FILE);
		write(Communicate.SUBMISSION);
		write(id);
		
		try {
		String path = in.readUTF();// "../SubmissionDownload/";
		//path += in.readUTF();
		byte[] content = (byte[]) in.readObject();
		if (content==null) UserView.displayErrorMessage("Assignment file doesn't exist. Try emailing your Professor.");
		File newFile = new File(path);
		try {
			if (!newFile.exists())
				newFile.createNewFile();
			FileOutputStream writer = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(writer);
			bos.write(content);
			System.err.println("FIle downloaded");
			bos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * gets the assignmetn specified and downloads it to client machine
	 * @param id id of assignment
	 * @return sucess of download process
	 */
	public boolean downloadAssignment(int id) {
		write(Communicate.GET);
		write(Communicate.FILE);
		write(Communicate.ASSIGNMENT);
		write(id);
		
		// I give u this ^ u give me a byte array file 
		
		try {
		String path = in.readUTF();// "../AssignmentDownload/";
		//path += in.readUTF();
		
		byte[] content = (byte[]) in.readObject();
		if (content==null) UserView.displayErrorMessage("Assignment file doesn't exist. Try emailing your Professor.");
		File newFile = new File(path);
		try {
			if (!newFile.exists())
				newFile.createNewFile();
			FileOutputStream writer = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(writer);
			bos.write(content);
			System.err.println("File downloaded.");
			bos.close();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * gets email from server
	 * @param id of user for which email to recive
	 * @return email as string
	 */
	public String getEmail(int id) {
		writeTag(Communicate.GET);
		writeTag(Communicate.EMAIL);
		write(id);
		String get = readString();
		System.err.println("getting email" + get);
		return get;
	}
	/**
	 *  gets emails of prof followed by all enrolled students 
	 *  eg. 'profEmail@x.com, hap@gmail.com, hola@me.com, '
	 * @param courseID
	 * @return
	 */
	
	public String getEmails(int courseID) {
		writeTag(Communicate.GET);
		writeTag(Communicate.EMAILS);
		write(courseID);
		System.err.println("wrote get - emails - courseID");
		return readString();
	}
	
}
