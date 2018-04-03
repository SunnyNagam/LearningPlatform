package format;

import java.util.ArrayList;

public class ChatRoom {
	private ArrayList<Message> message;
	
	public void add(Message m) {
		message.add(m);
	}
	
	public ArrayList<Message> getAll(){
		return message;
	}
}
