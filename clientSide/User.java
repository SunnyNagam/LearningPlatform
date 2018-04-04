package clientSide;

public abstract class User {
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
	
	abstract void instantiatePanels();
	abstract void assignButtons(Controller c);
}
