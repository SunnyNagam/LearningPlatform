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

import format.Communicate;

/**
 * @author keenangaudio
 *
 */
class DBHelper implements DBHandler, format.Communicate {
	private final int MAX_ATTEMPTS = 5;
	private Connection jdbc_connection;
	private PreparedStatement statement;

	private static final String dbName = "LearningPlatformDB", dataFile = ".txt";
	private static final String[] tables = { "Users", "Courses", "EnrollmentChart", "Assignments", "Submissions",
			"Grades" };
	
	/**
	 * 	keys[tableType][keyType]
	 */
	private static final String[][] keys = {
			{"ID","EMAIL","FIRST_NAME","LAST_NAME","PASSWORD","TYPE"},	// USER TABLE
			{"ID", "PROF_ID", "NAME", "ACTIVE"}							// COURSES TABLE
	};

	private static final String connectionInfo = "jdbc:mysql://localhost:3306/", SSLtag = "?useSSL=false",
			login = "root", password = "bacon"; // bacon if keenan

	DBHelper() {
		int attempts = 0;
		while (attempts <= MAX_ATTEMPTS) {
			try {
				// If this throws an error, make sure you have added the mySQL connector JAR to
				// the project
				Class.forName("com.mysql.jdbc.Driver");

				// If this fails make sure your connectionInfo and login/password are correct
				jdbc_connection = DriverManager.getConnection(connectionInfo + dbName + SSLtag, login, password);

				jdbc_connection.setAutoCommit(false);
				System.out.println("Connected to: " + connectionInfo + dbName + SSLtag + "\n");
				attempts = MAX_ATTEMPTS + 1;
			} catch (SQLException e) {
				attempts++;
				System.err.println("No database, attempting to create one.");
				try {
					createDB();
				} catch (Exception e1) {
					System.err.println("Error creating database.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see serverSide.DBHandler#createDB()
	 */
	@Override
	public void createDB() throws IOException, SQLException, Exception { // make this do the throw better
		try {
			jdbc_connection = DriverManager.getConnection(connectionInfo + SSLtag, login, password);
			statement = jdbc_connection.prepareStatement("CREATE DATABASE " + dbName);
			statement.executeUpdate();

			jdbc_connection = DriverManager.getConnection(connectionInfo + dbName + SSLtag, login, password);
			System.err.println("Created Database " + dbName);
			createTables();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTables() {		//TODO do this for keys somehow
		createUserTable(tables[0]);
		createCourseTable(tables[1]);
		createEnrollTable(tables[2]);
		createAssignTable(tables[3]);
		createSubTable(tables[4]);
		createGradeTable(tables[5]);
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
	 * we may need to add specific classes, such as addUser() or addCourse()
	 */
	@Override
	public void addRow(String[] data) throws IOException, SQLException, Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Prints the table of clients
	 */
	public void printTable(int tableType) {
		try {
			String sql = "SELECT * FROM " + tables[tableType];
			statement = jdbc_connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			System.out.println(tables[tableType]+":");
			while (set.next()) {
				System.out.println(set.getInt("ID") + " " + set.getString("PASSWORD"));
			}
			set.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	/*
	 * SEARCH CASES looking for a user
	 * 
	 *  TODO key might not always be a string!
	 */
	public ResultSet search(int tableType, int keyType, String key) throws IOException, SQLException, Exception {
		String sql = "SELECT * FROM " + tables[tableType] + " WHERE " + keys[tableType][keyType] + "=" + "?";
		ResultSet set;
		statement = jdbc_connection.prepareStatement(sql);
		statement.setString(1, key);
		set = statement.executeQuery();
		return set;
	}
	
	@Override
	public ResultSet search(int keyType, String key) throws IOException, SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ResultSet search(int tableType, int keyType, int key) throws IOException, SQLException, Exception {
		String sql = "SELECT * FROM " + tables[tableType] + " WHERE " + keys[tableType][keyType] + "=" + "?";
		//String sql = "SELECT * FROM " + tables[tableType];
		ResultSet set;
		statement = jdbc_connection.prepareStatement(sql);
		statement.setInt(1, key);
		System.out.println(sql);
		set = statement.executeQuery();
		return set;
	}

	// {"Users", "Courses", "EnrollmentChart", "Assignments", "Submissions",
	// "Grades"};
	private String parseTableTag(int tag) {
		switch (tag) {
		// search for stud or prof in the user table
		case Communicate.STUDENT:
		case Communicate.PROFESSOR:
			return tables[0]; // userTable

		// search for available courses in the course table
		case Communicate.COURSE:
			return tables[1];

		case Communicate.ENROLL:
		case Communicate.UNENROLL:
			return tables[2];

		case Communicate.FILE:
		}
		return null; // just in case (lol puns)
	}

	private String parseColTag(int tag) {
		switch (tag) {
		case Communicate.EMAIL:

		case Communicate.COURSE:

		case Communicate.NAME:

		}
		return null;
	}

	private void createGradeTable(String tableName) {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "ASSIGN_ID INT(8) NOT NULL, "
				+ "STUDENT_ID INT(8) NOT NULL, " + "COURSE_ID INT(8) NOT NULL, " + "ASSIGNMENT_GRADE INT(3), "
				+ "PRIMARY KEY ( id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.err.println("Created Grade table with name " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createSubTable(String tableName) {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "ASSIGN_ID INT(8) NOT NULL, "
				+ "STUDENT_ID INT(8) NOT NULL, " + "COURSE_ID INT(8) NOT NULL, " + "PATH VARCHAR(100) NOT NULL, "
				+ "TITLE VARCHAR(50), " + "SUBMISSION_GRADE INT(3), " + "PRIMARY KEY ( id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.err.println("Created Submissions table with name " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createAssignTable(String tableName) {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "COURSE_ID INT(8) NOT NULL, "
				+ "TITLE VARCHAR(50), " + "PATH VARCHAR(100), " + "ACTIVE BIT(1) NOT NULL, " + "DUE_DATE VARCHAR(16), "
				+ "PRIMARY KEY ( id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.err.println("Created Assignment table with name " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createEnrollTable(String tableName) {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "STUDENT_ID INT(8) NOT NULL, "
				+ "COURSE_ID INT(8) NOT NULL, " + "PRIMARY KEY ( id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.err.println("Created Enrollment table with name " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createCourseTable(String tableName) {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "PROF_ID INT(8) NOT NULL, "
				+ "NAME VARCHAR(50) NOT NULL, " + "ACTIVE BIT(1) NOT NULL," + "PRIMARY KEY ( id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.err.println("Created Courses table with name " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createUserTable(String tableName) {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "EMAIL VARCHAR(50) NOT NULL, "
				+ "FIRST_NAME VARCHAR(50) NOT NULL, " + "LAST_NAME VARCHAR(50) NOT NULL, "
				+ "PASSWORD VARCHAR(50) NOT NULL, " + "TYPE INT NOT NULL, " + "PRIMARY KEY ( id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.err.println("Created User table with name " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addUser(User user, String pass) throws IOException, SQLException, Exception {
		String sql = "INSERT INTO " + tables[0] + " VALUES (?, ?, ?, ?, ?, ?);";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, user.getId());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, pass);
			statement.setInt(6, user.getUserType());
			statement.executeUpdate();
			jdbc_connection.commit();		// idk why this is needed but it just is and it took me a hour and a half to figure this out :(
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
//
// int attempts = 0;
// while (attempts <= MAX_ATTEMPTS) {
// try{
// // If this throws an error, make sure you have added the mySQL connector JAR
// to the project
// Class.forName("com.mysql.jdbc.Driver");
//
// // If this fails make sure your connectionInfo and login/password are correct
// jdbc_connection = DriverManager.getConnection(connectionInfo, login,
// password);
// jdbc_connection.setAutoCommit(false);
// System.out.println("Connected to: " + connectionInfo + "\n");
// attempts = MAX_ATTEMPTS + 1;
// }
// catch(SQLException e) {
// attempts++;
// System.err.println("No database, attempting to create one.");
// try { createDB(); }
// catch (Exception e1) { System.err.println("Error creating database."); }
// } catch(Exception e) { e.printStackTrace(); }
// }
