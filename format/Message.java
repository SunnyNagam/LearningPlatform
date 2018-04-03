package format;

public class Message {
	private String message;
	private String userName;
	private String userType;
	
	
	public String getMessage() {
		return new String(userName + " (" + userType + "): \n" + message);
	}
}
