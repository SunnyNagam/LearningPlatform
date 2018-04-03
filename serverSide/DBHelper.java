/**
 * 
 */
package serverSide;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author keenangaudio
 *
 */
class DBHelper implements DBHandler, format.Communicate {
	
	private Connection jdbc_connection;
	private PreparedStatement statement;
	private String databaseName = "LearningPlatformDB", dataFile = ".txt";
	private String[] tables = {"Users", "Courses", "EnrollmentChart", "Assignments", "Submissions", "Grades"};
	/**
	 * @see serverSide.DBHandler#createDB()
	 */
	@Override
	public void createDB() throws IOException, SQLException, Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see serverSide.DBHandler#removeDB()
	 */
	@Override
	public void removeDB() throws IOException, SQLException, Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see serverSide.DBHandler#addRow(java.lang.String[])
	 */
	@Override
	public void addRow(String[] data) throws IOException, SQLException, Exception {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see serverSide.DBHandler#UpdateInfo(int, int, java.lang.String, int)
	 */
	@Override
	public void UpdateInfo(int clientType, int clientID, String info, int InsertType)
			throws IOException, SQLException, Exception {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see serverSide.DBHandler#search(int, java.lang.String)
	 */
	@Override
	public ResultSet search(int keyType, String key) throws IOException, SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
