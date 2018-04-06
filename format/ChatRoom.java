package format;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatRoom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Message> message;
	
	public void add(Message m) {
		message.add(m);
	}
	
	public ArrayList<Message> getAll(){
		return message;
	}
}
