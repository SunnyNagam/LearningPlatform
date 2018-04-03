/**
 * 
 */
package clientSide;

import format.Communicate;

/**
 * @author keenangaudio
 *
 */
class Controller {
	Client client;
	
	Controller(Client c) {
		
	}
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//create port according to args, or use default value defined in communication
			boolean def = args.length > 1;
			String name = (def)? args[0] : "localhost";
			String port = (def)? args[1] : String.format("%d",Communicate.MIN_PORT);
			
			int portCheck = 0;
			if ((portCheck = Integer.parseInt(port)) < Communicate.MIN_PORT || portCheck > Communicate.MAX_PORT )
				throw new NumberFormatException("Invalid Port Number: Port " + port + " out of range.");

			// I have port-forwarded ports 4200 - 4212, they are unused on IANA,but unofficially used for 'vrml-multi-use' 
			Client client = new Client(name, port);	
			System.out.println("The server is " + ((client.connected)?"":"not ") + "running on port " + port);
			
			client.communicate();
			Controller c = new Controller(client);
			
		} catch (NumberFormatException e) { System.out.println( e.getMessage() ); }
		
		
	}

}
