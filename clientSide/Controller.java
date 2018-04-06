/**
 * 
 */
package clientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clientSide.gui.LoginPanel;
import clientSide.gui.PanelList;
import clientSide.gui.UserView;
import format.Communicate;

/**
 * @author keenangaudio
 *
 */
class Controller {
	Client client;
	User user;
	UserView gui;
	int clientType;
	
	Controller(Client c) {
		this.client = c;
		this.gui = new UserView(Communicate.PROGRAM_NAME);
		
		login();
	}
	private void login () {
		gui.setVisible(true);
		LoginPanel pan = gui.getLoginPanel();
		
		pan.getSub().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = pan.getUser();
				String password = pan.getPass();
				
				int type = client.attemptLogin(userName, password);
				 
				if(type == Communicate.INVALID) gui.displayErrorMessage("Invalid Credentials");
				else {
					clientType = type;
					
					setupClient(type, userName);
				}
			}
		});
	}
	
	protected void setupClient(int type, String name) {
		name = String.format( "%08d", Integer.parseInt(name) );
		
		if (type == Communicate.STUDENT) user = new Student();
		else if (type == Communicate.PROFESSOR) user = new Professor();
		else {
			gui.displayErrorMessage("Fatal error.");
			System.exit(-1);
		}
		
		user.id = Integer.parseInt(name);
		
		gui.addPanels( user.instantiatePanels() );
		System.err.println("setupClient()");
		gui.initializeView(name, type);
		user.assignButtons(this);
		gui.paintAll(gui.getGraphics());
		//gui.switchWindow(PanelList.MY_COURSES);
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
			System.out.println("The client is " + ((client.connected)?"":"not ") + "connected to"
					+ " server " + name + " on port " + port);
			
			if(!client.connected) System.exit(-1);
			
			client.communicate();
			Controller c = new Controller(client);
			
		} catch (NumberFormatException e) { System.out.println( e.getMessage() ); }
		
		
	}

}
