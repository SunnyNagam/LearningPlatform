/**
 * 
 */
package clientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import format.Communicate;

/**
 * @author keenangaudio
 *
 */
public class Client {
	
	boolean connected;
	Socket cSocket;
	ObjectInputStream in;
	ObjectOutputStream out;
	
	Client (String name, String port) {
		connected = false;
		try {
			cSocket = new Socket( name, Integer.parseInt(port) );
			out = new ObjectOutputStream(cSocket.getOutputStream());
			connected = true;
		} catch (Exception e) {}
	}
	void communicate() {
		try {
			in = new ObjectInputStream(cSocket.getInputStream());
			while(true) {
				int tag = in.readInt();
				parseTag(tag);
			}
		} catch (IOException e) {}
	}
	private void parseTag(int tag) {
		switch (tag) {
		case Communicate.LOGIN :
			login();
			break;
		case Communicate.GET :
			get();
			break;
		case Communicate.REFRESH :
			refresh();
			break;
		case Communicate.SYNC :
			sync();
			break;
		case Communicate.DISCONNECT :
			disconnect();
			break;
		}
	}
	private void login() {
		try {
		//submit username
		//out.writeUTF(String);
		//submit password
		
		// read my type
		int type = in.readInt();
		if ( check(type) ) disconnect();
		} catch (IOException e) {}
	}
	private void get() {
		//read type
		
		//read object
		
	}
	private void refresh() {
		//read type
		
		//write object
		
	}
	private void sync() { //maybe
		// read type
		// read object
		
		// compare object
		// write back
	}
	private void disconnect() {
		// write disconnect back
		
		// close everything 
		
		// notify controller
	}
	private boolean check(int input) {
		return (input == Communicate.DISCONNECT);
	}
}
