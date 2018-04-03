/**
 * 
 */
package serverSide;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author keenangaudio
 *
 */
class DBHelper implements DBHandler, format.Communicate {
	private final int MAX_ATTEMPTS = 5;
	private Connection jdbc_connection;
	private PreparedStatement statement;
	
	private static final String dbName = "LearningPlatformDB", 
								dataFile = ".txt";
	private static final String[] tables = 
		{"Users", "Courses", "EnrollmentChart", "Assignments", "Submissions", "Grades"};
	private static final String connectionInfo = "jdbc:mysql://localhost:3306/", 
								SSLtag   = "?useSSL=false",
								login 	 = "root", 
								password = "bacon";
	
	DBHelper() {
		int attempts = 0;
		while (attempts <= MAX_ATTEMPTS) {
			try{
				// If this throws an error, make sure you have added the mySQL connector JAR to the project
				Class.forName("com.mysql.jdbc.Driver");

				// If this fails make sure your connectionInfo and login/password are correct
				jdbc_connection = DriverManager.getConnection(connectionInfo + dbName + SSLtag, login, password);
				
				jdbc_connection.setAutoCommit(false);
				System.out.println("Connected to: " + connectionInfo  + dbName + SSLtag + "\n");
				attempts = MAX_ATTEMPTS + 1;
			}
			catch(SQLException e) { 
				attempts++;
				System.err.println("No database, attempting to create one.");
				try { createDB(); } 
				catch (Exception e1) { System.err.println("Error creating database."); }
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	/**
	 * @see serverSide.DBHandler#createDB()
	 */
	@Override
	public void createDB() throws IOException, SQLException, Exception { //make this do the throw better
		try {
			jdbc_connection = DriverManager.getConnection(connectionInfo + SSLtag, login, password);
			statement = jdbc_connection.prepareStatement("CREATE DATABASE " + dbName);
			statement.executeUpdate();
			System.out.println("Created Database " + dbName);
			
		} 
		catch (SQLException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
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
//
//int attempts = 0;
//while (attempts <= MAX_ATTEMPTS) {
//	try{
//		// If this throws an error, make sure you have added the mySQL connector JAR to the project
//		Class.forName("com.mysql.jdbc.Driver");
//
//		// If this fails make sure your connectionInfo and login/password are correct
//		jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
//		jdbc_connection.setAutoCommit(false);
//		System.out.println("Connected to: " + connectionInfo + "\n");
//		attempts = MAX_ATTEMPTS + 1;
//	}
//	catch(SQLException e) { 
//		attempts++;
//		System.err.println("No database, attempting to create one.");
//		try { createDB(); } 
//		catch (Exception e1) { System.err.println("Error creating database."); }
//	} catch(Exception e) { e.printStackTrace(); }
//}
