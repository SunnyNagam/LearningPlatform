package serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import format.Communicate;

public class Connector {

	boolean running;
	ServerSocket sSocket;
	DBHandler db;
	ExecutorService pool;
	

	Connector(String port) {
		running = false;
		
		try {
			sSocket = new ServerSocket(Integer.parseInt(port));
			pool = Executors.newCachedThreadPool();
			running = true;
		} catch (Exception e) {
		}
	}

	void accept() {
		if (!running)
			return;
		while (true) {
			try {
				Socket cSocket = sSocket.accept();
				System.out.println("Client connection received.");
				pool.execute(new Instance(cSocket.getInputStream(), cSocket.getOutputStream()));
				
				System.out.println("Connector: Client sent to external thread.");
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	void startDB() {
		db = new DBHelper();
	}

	/**
	 * starts the
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// create port according to args, or use default value defined in communication
			String port = (args.length > 0) ? args[0] : String.format("%d", Communicate.MIN_PORT);

			int portCheck = 0;
			if ((portCheck = Integer.parseInt(port)) < Communicate.MIN_PORT || portCheck > Communicate.MAX_PORT)
				throw new NumberFormatException("Invalid Port Number: Port " + port + " out of range.");

			// I have port-forwarded ports 4200 - 4212, they are unused on IANA, but
			// unofficially used for 'vrml-multi-use'
			Connector c = new Connector(port);
			System.out.println("The server is " + ((c.running) ? "" : "not ") + "running on port " + port);
			c.startDB();
			
		// TODO THIS IS JUST FOR TESTING PURPOSES REMOVE THIS LATER DUDES	------------------
				serverSide.User steve = new User(Communicate.PROFESSOR,"Steve","Boring","steveBoring@gmail.com",1);
				serverSide.User carl = new User(Communicate.STUDENT,"CARL","LAME","killmenow@gmail.com",7);
						// for some reason he doesn't show up when we search for him
				try {
					//c.db.addUser(steve, "poop");
					c.db.addUser(carl, "y");
				} catch (Exception e) {
					e.printStackTrace();
				} 
				c.db.printTable(0);
		// --------------------------------------------------------------------------------
			
			c.accept();

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
	}
}
