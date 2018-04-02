package serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Format.Communicate;

public class Connector {
	
	boolean running;
	ServerSocket sSocket;
	Socket cSocket;
	
	ExecutorService pool;
	
	
	Connector(String port) {
		try {
		sSocket = new ServerSocket(Integer.parseInt(port));
		pool = Executors.newCachedThreadPool();
		running = true; 
		} catch (Exception e) {}
		
	}
	void accept() {
		if (!running) return;
		
		while (true) {
			try {
			cSocket = sSocket.accept();
			//pool.execute( new Instance(cSocket.getInputStream(), cSocket.getOutputStream()) );
			} catch(IOException e) { System.err.println(e.getMessage()); }
		}
		
	}
	/**
	 * starts the 
	 * @param args
	 */
	public static void main(String [] args) {
		try {
			//create port according to args, or use default value defined in communication
			String port = (args.length > 0)? args[0] : String.format("%d",Communicate.MIN_PORT);
			
			int portCheck = 0;
			if ((portCheck = Integer.parseInt(port)) < Communicate.MIN_PORT || portCheck > Communicate.MAX_PORT )
				throw new NumberFormatException("Invalid Port Number: Port " + port + " out of range.");

			// I have port-forwarded ports 4200 - 4212, they are unused on IANA,but unofficially used for 'vrml-multi-use' 
			Connector c = new Connector(port);	
			System.out.println("The server is " + ((c.running)?"":"not ") + "running on port " + port);
			
			c.accept();
			
		} catch (NumberFormatException e) { System.out.println( e.getMessage() ); }
	}
}
