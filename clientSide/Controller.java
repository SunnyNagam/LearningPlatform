/**
 * 
 */
package clientSide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clientSide.gui.LoginPanel;
import clientSide.gui.MyCoursesPanel;
import clientSide.gui.PanelList;
import clientSide.gui.StudentsPanel;
import clientSide.gui.UserView;
import format.Communicate;
import format.Course;

/**
 * @author keenangaudio
 *
 */
public class Controller {
	public Client client;
	User user;
	public int selectedCourse = -1;
	UserView gui;
	int clientType;
	protected String selectedName;
	
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
		
		if (type == Communicate.STUDENT) {
			user = new Student();
			System.err.println("created student");
		}
		else if (type == Communicate.PROFESSOR) { 
			user = new Professor();
			System.err.println("created prof");
			}
		else {
			gui.displayErrorMessage("Fatal error.");
			System.exit(-1);
		}
		
		user.id = Integer.parseInt(name);
		
		gui.addPanels( user.instantiatePanels() );
		
		System.err.println("setupClient()");
		initializeView(name, type);
		user.assignButtons(this);
		gui.paintAll(gui.getGraphics());
		//gui.switchWindow(PanelList.MY_COURSES);
	}
	private void initializeView(String name, int type) {
		System.err.println("Courses menu action started");
		ArrayList<Course>  set = client.getCourses(user.id);
		System.err.println("Got responce set from db. "+set.size());
		((MyCoursesPanel) gui.getPanels()[PanelList.MY_COURSES]).refreshData(set);
		gui.initializeView(name, type);
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
