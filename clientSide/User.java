package clientSide;

import javax.swing.JPanel;
import clientSide.gui.PanelList;

abstract class User implements PanelList{
	public int getUserType() {
		return userType;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public int getId() {
		return id;
	}
	int userType;
	String firstName, lastName, email;
	int id;
	
	abstract JPanel[] instantiatePanels();
	abstract void assignButtons(Controller c);
}
