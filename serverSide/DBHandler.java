/**
 * 
 */
package serverSide;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import format.Assignment;
import format.Course;
import format.Submission;

/**
 * @author keenangaudio, sunnynagam
 *
 */
interface DBHandler {
	/**
	 * creates a database to be used
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	void createDB() 
			throws IOException, SQLException, Exception;
	
	/**
	 * deletes the database
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	void removeDB() 
			throws IOException, SQLException, Exception;
	
	/**
	 * adds a row based on the input strings
	 * @param data the input strings
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	void addRow(String[] data) 
			throws IOException, SQLException, Exception;
	
	/**
	 * Note: Password should not be stored in user class, only db for security, therefor we add it seperately.
	 * @param user
	 * @param pass
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	void addUser(User user, String pass)	 
			throws IOException, SQLException, Exception;
	
	/**
	 * updates a row according to input
	 * @param clientType the requestor's type
	 * @param clientID the id of the requestor
	 * @param info the new value 
	 * @param InsertType the field to be updated
	 */
	void UpdateInfo(int clientType, int clientID, String info, int InsertType)
			throws IOException, SQLException, Exception;
	
	/**
	 * prints the table to system.out
	 * @param tableType the table to be printed
	 */
	void printTable(int tableType);
	
	/**
	 * search from specified table, in specified column, for a key
	 * @param tableType the table to search
	 * @param extraKeyType the type of key to search
	 * @param key 
	 * @return ResultSet, may be empty
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	ResultSet search(int tableType, String keyType, String key) 
			throws IOException, SQLException, Exception;
	
	/**
	 * search from specified table, in specified column, for a key
	 * @param tableType the table to search
	 * @param extraKeyType the type of key to search
	 * @param key 
	 * @return ResultSet, may be empty
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	ResultSet search(int tableType, String keyType, int key) 
			throws IOException, SQLException, Exception;
	
	/**
	 * A simplified search, returns all matching rows from most logical table
	 * @param keyType
	 * @param key
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	ResultSet search(int keyType, String key) 
			throws IOException, SQLException, Exception;
	
	/**
	 * stores a file on the server machine, and records the path to it in the database
	 * @param s the submission information
	 * @param f the file to store
	 * @throws IOException if fails to save file, SQLException if fails to write path to database
	 */
	public void storeFile(Submission s, File f) 
			throws IOException, SQLException;

	/**
	 * adds a new course to the database
	 * @param x the course containing information to be added to the database
	 */
	void addCourse(Course x);
	
	/**
	 * adds a new assignment to the database
	 * @param x the assignment containing information to be added to the database
	 */
	String addAssignment(Assignment x);
	
	/**
	 * toggles the enrollment of specified user in specified course
	 * @param studentID the student id
	 * @param courseID the course id
	 */
	void toggleEnroll(int studentID, int courseID) throws SQLException;
	
	/**
	 * Returns ResultSet containing row in enrollment chart containing studentID and courseID if it exists
	 * @param studentID student to search for
	 * @param courseID course to study for
	 * @return
	 */
	ResultSet enrolled(int studentID, int courseID);

	/**
	 * returns all submissions for a given assignment
	 * @param assignID
	 * @return
	 */
	ResultSet submissions(int assignID);
	
	/**
	 * toggles the active bit for the given course
	 * @param id the id of the course to be toggled
	 */
	void toggleActive(int id);

	/**
	 * returns a resultset rows in a specified table for type keytype matching key
	 * @param string the table to search
	 * @param keyType
	 * @param key
	 * @return
	 */
	ResultSet search(String string, String keyType, int key) 
			throws IOException, SQLException, Exception;

	/**
	 * toggles the active bit for the given course
	 * @param key the id of the course to be toggled
	 */
	void toggleAssActive(int key)
			throws IOException, SQLException, Exception;

	/**
	 * returns a resultset of rows in the enrollment chart matching studentID
	 * @param studentID
	 * @return
	 */
	ResultSet getEnrolledCourses(int studentID);

	/**
	 * * updates a row according to input
	 * @param table 
	 * @param col the column to update
	 * @param value the value 
	 * @param id the id of the row to be updated
	 */
	void update(int table, String col, int value, int id);

	/**
	 * serached the given table for sql section string matching args
	 * @param table
	 * @param string eg. "id=? AND name=? AND course_id=?"
	 * @param args eg. 54, 33, 66
	 * @return
	 * @throws Exception
	 */
	ResultSet searchf(int table, String string, int ... args) throws Exception;

	/**
	 * adds grade to grade table
	 * @param maxGrade the grade to add
	 * @param assignID the assignment
	 * @param studID the student
	 * @param courseID the course
	 */
	void addGrade(int maxGrade, int assignID, int studID, int courseID);
	/**
	 * refresh current selection (unused)
	 * @throws SQLException
	 */
	void refresh() throws SQLException;
	/**
	 * notifies db of subsequent request (lock db)
	 * @throws SQLException
	 */
	void start() throws SQLException;
	/**
	 * notifies db of request end (unlock db)
	 * @throws SQLException
	 */
	void end() throws SQLException;
	/**
	 * returns all submissions from student to assignment matching AssignID
	 * @param assignID
	 * @param student
	 * @return
	 */
	ResultSet submissions(int assignID, int student);
	/**
	 * adds a submission to db
	 * @param x the submission containing data to be sent to the database
	 * @return
	 */
	String addSubmission(Submission x);
	
	/**
	 * returns all grades for student
	 * @param stuID
	 * @return
	 */
	ResultSet getGrades(int stuID);
}
