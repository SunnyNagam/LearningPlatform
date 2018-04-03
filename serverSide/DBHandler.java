/**
 * 
 */
package serverSide;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	void createDB() throws IOException, SQLException, Exception;
	
	/**
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	void removeDB() throws IOException, SQLException, Exception;
	
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
	 * @param keyType
	 * @param key
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	ResultSet search(int keyType, String key) 
			throws IOException, SQLException, Exception;
}
