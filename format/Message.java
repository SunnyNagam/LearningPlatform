package format;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private String userName;
	private String userType;
	
	/**
	 * 
	 * @return a string
	 */
	public String getMessage() {
		return new String(userName + " (" + userType + "): \n" + message);
	}
}
