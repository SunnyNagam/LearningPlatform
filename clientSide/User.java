package clientSide;

import java.io.Serializable;

import javax.swing.JPanel;
import clientSide.gui.PanelList;

public abstract class User implements PanelList, Serializable{
	private static final long serialVersionUID = 2L;
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
	
	@Override 
	public String toString() {
		return String.format("%11d %13s %13s", id, firstName, lastName);
	}
	
	abstract JPanel[] instantiatePanels();
	abstract void assignButtons(Controller c);
}
