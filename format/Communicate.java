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
	static final int REFRESH	= 0x43;
	static final int LOGIN 		= 0x44;
	static final int GET		= 0x45;
	static final int SYNC		= 0x46;
	
	// types
	static final int FILE		= 0x15;
	static final int EMAIL		= 0x16;
	static final int COURSE		= 0x20;
	static final int ASSIGNMENT	= 0x21;
	static final int SUBMISSION = 0x22;
	static final int CHATROOM	= 0x23;
	static final int MESSAGE	= 0x24;
	
}
