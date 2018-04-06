/**
 * 
 */
package serverSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import format.Communicate;
import format.Course;

/**
 * @author keenangaudio
 *
 */
class Instance implements Runnable {
	ObjectInputStream in;
	ObjectOutputStream out;
	int clientType;
	DBHandler helper;
	
	Instance (InputStream in_, OutputStream out_/*, modelhandler */) {
		clientType = 0;	// not a valid type
		try {
			//write connected
			out = new ObjectOutputStream(out_);
			//in = new ObjectInputStream(in_);
			out.writeInt(Communicate.CONNECTED);
			out.flush();
			
			System.out.println("Got wrote mate");
			
			//wait for response
			in = new ObjectInputStream(in_);
			System.out.println("Got input stream, waiting for input int");
			//check if response is what we're expecting
			if (in.readInt() != Communicate.CONNECTED ) throw new IOException("Unexpected response from Client.");
			System.out.println("Connection to client established.\n"
					+ "server instance running on " + Thread.currentThread().getName() );
			
			helper = new DBHelper();
		} catch (IOException e) { System.err.println(e.getMessage()); }
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
				System.err.println("Got command: "+tag);
				parseTag(tag);
			}
		} catch (IOException e) { e.printStackTrace(); }
	}
	private void parseTag(int tag) throws IOException {
		switch (tag) {
		case Communicate.LOGIN : login();
			break;
		case Communicate.GET : get();
			break;
		case Communicate.REFRESH : refresh();
			break;
		case Communicate.SYNC : sync();
			break;
		case Communicate.DISCONNECT : disconnect();
			break;
		}
	}
	private void login() {
		try {
			// read username
			int username = in.readInt();
			// read password
			String password = in.readUTF();
			// pass to modelhandler - get type or write Communicate.DB_ERROR
			// TODO for now just to test:
			
			ResultSet set = helper.search(0, "ID", String.format("%d",username) );
			if(!set.next()) {
				out.writeInt(Communicate.INVALID);
				System.out.println("Null set returned.");
			}
			else {
				set.beforeFirst();
				set.next();
				if(password.equals(set.getString("PASSWORD"))) {
					out.writeInt(clientType = set.getInt("TYPE"));
				}
				else {
					out.writeInt(Communicate.INVALID);
				}
			}
			out.flush();
		} 
		catch (IOException e) { e.printStackTrace(); } 
		catch (SQLException e) { e.printStackTrace(); } 
		catch (Exception e) { e.printStackTrace(); }
	}
	private void get() throws IOException {
		try {
			
			//read type && find object to send
			int type = in.readInt();
			if ( checkDisconnect(in) ) disconnect();
			System.err.println("getting type "+ type);
			get(type);
			
			//int status = (true) ? Communicate.DB_SUCCESS : Communicate.DB_ERROR;
			//out.writeInt(status);
			//write object (cases) 
		
		} catch (IOException e) {}
		
	}
	private void get(int tag) {
		switch (tag) {
		case Communicate.STUDENT: getStudent();
			break;
		case Communicate.COURSE: getCourses();
			break;
		case Communicate.EMAIL : emailResponse();
			break;
		case Communicate.FILE : fileResponse();
			break;
		case Communicate.MESSAGE : messageResponse();
			break;
		}
	}
	private void getCourses() {
		try {
			int key = in.readInt();
			System.err.println("getting courses from db");
			ResultSet r = helper.search(Communicate.COURSE, "PROF_ID", key );
			System.err.println("writing courses from db");
			
			out.writeObject( parseRRCourse(r) );
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.writeObject( null );
				out.flush();
			} catch (IOException e1) { e1.printStackTrace(); }
		}
	}
	private void getStudent() {
		try {
			System.err.println("getting students from db");
			ResultSet r = helper.search(Communicate.STUDENT, "TYPE", 8 );
			System.err.println("writing students from db");
			
			out.writeObject( parseRRUser(r) );
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.writeObject( null );
				out.flush();
			} catch (IOException e1) { e1.printStackTrace(); }
		}
	}
	
	private void messageResponse() {
		// read message
		
		// send message to model
	}
	private void emailResponse() {
		//read course
		
		//write prof email
		
	}
	private void fileResponse() {
		if (clientType == Communicate.STUDENT) {
			studentFile();
		} else {	//client type == prof
			profFile();
		}
			
	}
	private void studentFile() {
		//read student

		//read course

		//read assignment

		//read file
		
		//give file to model
	}
	private void profFile() {
		//read course

		//read assignment

		//read file
		
		//give file to model
	}
	private void refresh() { // maybe unnecessary
		//read type
		
		//read object
		
	}
	private void sync() { //maybe unnecessary
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
		
	}
	private void disconnect() throws IOException {
		// write disconnect back
		
		// close everything 
		
		throw new IOException ("Client requested disconnect.\n"
				+ "Shutting down on " + Thread.currentThread().getName() );
	}
	/**
	 * use whenever reading an input, checks for unexpected client disconnect
	 * @param input the object being read
	 * @return true if disconnect was received
	 */
	private boolean checkDisconnect(Object input) { 
		int check = 0;
		try {
			check = (int) input;
		} catch(ClassCastException e) {}
		return (check == Communicate.DISCONNECT);
	}
	private ArrayList<Course> parseRRCourse (ResultSet r) {
		ArrayList<Course> arr = new ArrayList<Course>();
		try {
			while (r.next()) {
				arr.add(Course.castRR(r));
			}
			System.err.println("Elements in set: "+arr.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	private ArrayList<String[]> parseRRUser (ResultSet r) {
		ArrayList<String[]> arr = new ArrayList<String[]>();
		try {
			while (r.next()) {
				//nt(int type, String first, String last, String email, int id)
				arr.add(new String[] {String.valueOf(r.getInt("TYPE")),r.getString("FIRST_NAME"),r.getString("LAST_NAME"),r.getString("EMAIL"),String.valueOf(r.getInt("ID"))});
			}
			System.err.println("Elements in set: "+arr.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	

}
