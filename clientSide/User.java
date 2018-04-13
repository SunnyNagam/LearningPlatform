package clientSide;

import java.io.Serializable;

import javax.swing.JPanel;
import clientSide.gui.PanelList;

public abstract class User implements PanelList, Serializable{
	private static final long serialVersionUID = 2L;
	/**
	 * 
	 * @return userType
	 */
	public int getUserType() {
		return userType;
	}
	/**
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	int userType;
	String firstName, lastName, email;
	int id;
	boolean inSelectedCourse = false;
	/**
	 * @return a string representation of the class
	 */
	@Override 
	public String toString() {
		return String.format("%11d %13s %13s %12s", id, firstName, lastName,(inSelectedCourse?"Enrolled":"Not Enrolled"));
	}
	/**
	 * sets whether or not the student is in the selected course
	 * @param c_ID
	 */
	public void currentCourse(boolean c_ID) {
		System.err.println("student " + id + " in course: " + c_ID);
		inSelectedCourse = c_ID;
	}
	
	abstract JPanel[] instantiatePanels();
	abstract void assignButtons(Controller c);
}
