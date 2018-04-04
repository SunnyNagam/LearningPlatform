/**
 * 
 */
package format;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public interface Communicate {
	// connection
	static final int MIN_PORT 	= 4200;
	static final int MAX_PORT 	= 4212;
	
	// commands
	static final int DB_ERROR	= 0x40;
	static final int DB_SUCCESS	= 0x41;
	
	static final int REFRESH	= 0x43;
	static final int LOGIN 		= 0x44;
	static final int GET		= 0x45;
	static final int SYNC		= 0x46;
	static final int DISCONNECT	= 0x47;
	static final int CONNECTED	= 0x48;
	static final int ENROLL		= 0x49;
	static final int UNENROLL	= 0x50;
	
	// types
	static final int STUDENT	= 0x08;
	static final int PROFESSOR	= 0x09;
	
	static final int STRING		= 0x14;
	static final int FILE		= 0x15;
	static final int EMAIL		= 0x16;
	static final int NAME		= 0x17;
	
	static final int COURSE		= 0x20;
	static final int ASSIGNMENT	= 0x21;
	static final int SUBMISSION = 0x22;
	static final int CHATROOM	= 0x23;
	static final int MESSAGE	= 0x24;
	
	// program attributes
	static final String PROGRAM_NAME = "Learning Platform";
	static final int minCommand = 0x40, maxCommand = 0x50;
	
}
