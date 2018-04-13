/**
 * 
 */
package format;

/**
 * @author keenangaudio, sunnynagam
 * uselful ints
 */
public interface Communicate {
	// connection
	static final int MIN_PORT 		= 4200;
	static final int MAX_PORT 		= 4212;
	
	// commands
	static final int DB_ERROR		= 0x40;
	static final int DB_SUCCESS		= 0x41;
	
	static final int TOGGLECOURSE	= 0x43;
	static final int LOGIN 			= 0x44;
	static final int GET			= 0x45;
	static final int SYNC			= 0x46;
	static final int DISCONNECT		= 0x47;
	static final int CONNECTED		= 0x48;
	static final int ENROLL			= 0x49;
	static final int ENROLLED		= 0x4a;
	static final int SEARCH_STRING 	= 0x4b;
	static final int SEARCH_NAME 	= 0x4c;
	
	// types
	static final int INVALID		= -0x1;
	
	static final int STUDENT		= 0x08;
	static final int PROFESSOR		= 0x09;
	
	static final int STRING			= 0x14;
	static final int FILE			= 0x15;
	static final int EMAIL			= 0x16;
	static final int EMAILS 		= 0x17;
	static final int NAME			= 0x18;
	
	static final int COURSE			= 0x20;
	static final int ASSIGNMENT		= 0x21;
	static final int SUBMISSION 	= 0x22;
	static final int CHATROOM		= 0x23;
	static final int MESSAGE		= 0x24;
	static final int GRADES			= 0x25;
	
	// program attributes
	static final String PROGRAM_NAME = "Learning Platform";
	static final int minCommand = 0x40, maxCommand = 0x50;
	
	
	
}
