/**
 * 
 */
package clientSide;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import format.Assignment;
import format.Communicate;
import format.Course;

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

	public void writeTag(int tag) {
		try {
			out.writeInt(tag);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(int toWrite) {
		try {
			out.writeInt(toWrite);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String toWrite) {
		try {
			out.writeUTF(toWrite);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int readInt() {
		try {
			return in.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			return Communicate.INVALID;
		}
	}
	public String readString() {
		try {
			return in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean readBoolean() {
		try {
			return in.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	void communicate() {
		try {
			while (true) {
				throw new IOException();

			}
		} catch (IOException e) {
		}
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

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

	public void addCourse(int id, String cName, String p_id, boolean b) {
		writeTag(Communicate.SYNC);
		writeTag(Communicate.COURSE);
		writeObject(new Course(id, cName, p_id, b));
	}

	private void writeObject(Object toWrite) {
		try {
			out.writeObject(toWrite);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public void toggleCourse(int selectedCourse) {
		writeTag(Communicate.TOGGLECOURSE);
		write(selectedCourse);
	}

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

	public void toggleEnroll(Student stu, int courseID) {
		writeTag(Communicate.ENROLL);
		write(stu.id);
		write(courseID);
	}

	public boolean inSelectedCourse(User usr,int selectedCourse) {
		writeTag(Communicate.ENROLLED);
		write(usr.id);
		write(selectedCourse);
		boolean t = readBoolean();
		System.err.println("CLIENT::student " + usr.id + " in course: " + t);
		return t;
	}

	public String getCourseName(int selectedCourse) {
		writeTag(Communicate.GET);
		writeTag(Communicate.COURSE);
		writeTag(Communicate.NAME);
		write(selectedCourse);
		String str = readString();
		str = (str==null)?"NULL":str;
		return str;
	}

	public void toggleAssignment(int ID) {
		writeTag(Communicate.ASSIGNMENT);
		write(ID);
	}

	public void upload(String title, String path, String due, boolean active, int course, byte[] file) {
		writeTag(Communicate.SYNC);
		writeTag(Communicate.ASSIGNMENT);
		String temp = path.substring(path.lastIndexOf("."),path.length());
		System.err.println(temp);
		writeObject(new Assignment(title,temp,active,course,-1,due));	// assign but path is 'default' and id is -1
		writeObject(file);
	}

}
