/**
 * 
 */
package serverSide;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import format.Submission;

/**
 * @author keenangaudio
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
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	void removeDB() 
			throws IOException, SQLException, Exception;
	
	/**
	 * 
	 * @param data 
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
	 * @param clientType
	 * @param clientID
	 * @param info
	 * @param InsertType
	 */
	void UpdateInfo(int clientType, int clientID, String info, int InsertType)
			throws IOException, SQLException, Exception;
	
	/**
	 * 
	 * @param tableType
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
	ResultSet search(int tableType, int keyType, String key) 
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
}
