package clientSide;

abstract class User {
	String userType;
	String userName;
	
	abstract void instantiatePanels();
	abstract void assignButtons(Controller c);
}
