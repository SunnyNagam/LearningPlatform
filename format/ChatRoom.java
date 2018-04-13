package format;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatRoom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Message> message;
	/**
	 * does nothing, is never used
	 * @param m
	 */
	public void add(Message m) {
		message.add(m);
	}
	/**
	 * 
	 * @return arraylist
	 */
	public ArrayList<Message> getAll(){
		return message;
	}
}
