package clientSide.gui;

public interface PanelList {
	public static final String[] AT	= {"LOGIN", "My Courses", 
			"Course", "Assignments", "Students;Grades", 
			"ChatRoom", "My Inbox", "Send New Email" /*add more here*/ };
	
	public static final int LOGIN		= 0;
	public static final int MY_COURSES	= 1;
	public static final int COURSE		= 2;
	public static final int ASSIGNMENTS	= 3;
	public static final int GRADES		= 4;	// students and grades will be used in either prof or student
	public static final int STUDENTS	= 4;	// but not both, so they have the same index
	public static final int CHATROOM	= 5;
	public static final int EMAIL_INBOX	= 6;
	public static final int EMAIL_MAKER	= 7;
	public static final int ARRAY_SIZE 	= 8;	// update this when adding panels to this list
}
