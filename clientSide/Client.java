/**
 * 
 */
package clientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

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
			out.writeInt(Communicate.CONNECTED);
			
			in = new ObjectInputStream(cSocket.getInputStream());
			connected = (in.readInt() == Communicate.CONNECTED);
		} catch (IOException e) { System.err.println(e.getMessage()); }
	}
	void communicate() {
		try {
			while(true) {
				throw new IOException();
				
			}
		} catch (IOException e) {}
	}
	
}
