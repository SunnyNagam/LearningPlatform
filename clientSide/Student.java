package clientSide;

public class Student extends User{

	public Student() {
		
	}
	public Student(String type, String first, String last, String email, int id) {
		this.userType = type;
		this.firstName = first;
		this.lastName = last;
		this.email = email;
		this.id = id;
	}
	
	@Override
	void instantiatePanels() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void assignButtons(Controller c) {
		// TODO Auto-generated method stub
		
	}
	
}
