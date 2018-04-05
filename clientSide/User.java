package clientSide;

import javax.swing.JPanel;
import clientSide.gui.PanelList;

public abstract class User implements PanelList{
	public String getUserType() {
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
	String userType;
	String firstName, lastName, email;
	int id;
	
	abstract JPanel[] instantiatePanels();
	abstract void assignButtons(Controller c);
}
