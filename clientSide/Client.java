/**
 * 
 */
package clientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;

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

}
