/**
 * 
 */
package serverSide;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import format.Communicate;
import format.Course;
import format.Submission;

/**
 * @author keenangaudio
 *
 */
class DBHelper implements DBHandler, format.Communicate {
	private final int MAX_ATTEMPTS = 5;
	private Connection jdbc_connection;
	private PreparedStatement statement;
	private int enrollCount = 0;

	private static final String dbName = "LearningPlatformDB", dataFile = ".txt";
	private static final String[] tables = { "Users", "Courses", "EnrollmentChart", "Assignments", "Submissions",
			"Grades" };
	

	private static final String connectionInfo = "jdbc:mysql://localhost:3306/", SSLtag = "?useSSL=false",
			login = "root", password = "rootpass"; // bacon if keenan, rootpass if sunny

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
				if (!makeDirPath(tables[3])) {System.err.println("Could Not Create Assignment Directory.");}
				if (!makeDirPath(tables[4])) {System.err.println("Could Not Create Submission Directory.");}

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
	private boolean makeDirPath(String folder) {
		boolean fDir = false;
		int fileTries = 0;
		while (!fDir && fileTries++ < MAX_ATTEMPTS + 1)
			fDir = (new File("../" + folder)).mkdirs();
		return fDir;
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
	private String parseTableType(int type) {
		switch (type) {
		case Communicate.PROFESSOR :
		case Communicate.STUDENT :
			return tables[0];
		case Communicate.COURSE :
			return tables[1];
		case Communicate.ENROLL :
			return tables[2];
		case Communicate.ASSIGNMENT :
			return tables[3];
		case Communicate.SUBMISSION :
			return tables[4];
		case Communicate.GRADES :
			return tables[5];
		default : ;
		}
		return tables[0];
	}
	/**
	 * to only be used for keys[][i], TODO
	 * @param type i
	 * @return
	 */
	private int parseTableInt(int type) {
		switch (type) {
		case Communicate.PROFESSOR :
			return 1;
		case Communicate.STUDENT :
			return 4;
		case Communicate.COURSE :
			return 0;
		case Communicate.ENROLL :
			return 2;
		case Communicate.ASSIGNMENT :
			return 3;
		case Communicate.SUBMISSION :
			return 4;
		case Communicate.GRADES :
			return 5;
		default : ;
		}
		return 0;
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
			String sql = "SELECT * FROM " + parseTableType(tableType);
			statement = jdbc_connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			System.out.println(parseTableType(tableType)+":");
			while (set.next()) {
				System.out.println(String.format("%8d",set.getInt("ID")) + " " + set.getString("PASSWORD"));
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
	public ResultSet search(int tableType, String keyType, String key) throws IOException, SQLException, Exception {
		System.err.println("Searching in table: "+parseTableType(tableType) + " where "
				+ keyType +" = "+key);
		String sql = "SELECT * FROM " + parseTableType(tableType) + " WHERE " + 
				keyType + "=" + "?";
		ResultSet set;
		statement = jdbc_connection.prepareStatement(sql);
		statement.setString(1, key);
		set = statement.executeQuery();
		return set;
	}
	/**
	 * searches enrollment table
	 */
	@Override
	public ResultSet search(int keyType, String key) throws IOException, SQLException, Exception {
		String sql = "SELECT * FROM " + tables[2] + " WHERE " + 
				keyType + "=" + "?";
		//String sql = "SELECT * FROM " + parseTableType(tableType);
		ResultSet set;
		statement = jdbc_connection.prepareStatement(sql);
		statement.setInt(1, Integer.parseInt(key) );
		System.err.println(statement);
		set = statement.executeQuery();
		return set;
	}
	@Override
	public ResultSet search(int tableType, String keyType, int key) throws IOException, SQLException, Exception {
		System.err.println("Searching in table: "+parseTableType(tableType) + " where "
				+ keyType +" = "+key);
		String sql = "SELECT * FROM " + parseTableType(tableType) + " WHERE " + 
				keyType + "=" + "?";
		//String sql = "SELECT * FROM " + parseTableType(tableType);
		ResultSet set;
		statement = jdbc_connection.prepareStatement(sql);
		statement.setInt(1, key);
		System.err.println(statement);
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
				+ "COURSE_ID INT(8) NOT NULL, ENROLLED BIT(1), " + "PRIMARY KEY ( id ))";
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
			System.err.println(user.getUserType());
			statement.executeUpdate();
			jdbc_connection.commit();		// idk why this is needed but it just is and it took me a hour and a half to figure this out :(
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void storeFile(Submission s, File f) throws IOException, SQLException {
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString();
		
		
	}
	@Override
	public void addCourse(Course x) {
		String sql = "INSERT INTO " + tables[1] + " VALUES (?, ?, ?, ?);";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, getRows(tables[1]));
			statement.setString(2, x.getProfName());
			statement.setString(3, x.getCourseName());
			statement.setBoolean(4, x.isActive());
			statement.executeUpdate();
			jdbc_connection.commit();		// idk why this is needed but it just is and it took me a hour and a half to figure this out :(
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void toggleEnroll(int studentID, int courseID) throws SQLException {
		if (enrolled(studentID,courseID).first()) {
			String sql = "DELETE FROM " + tables[2] + "WHERE STUDENT_ID=? AND COURSE_ID=?;";
			
				statement = jdbc_connection.prepareStatement(sql);
				statement.setInt(1, studentID);
				statement.setInt(2, courseID);
				enrollCount--;
		} else {
			String sql = "INSERT INTO " + tables[2] + " SET ID=?, STUDENT_ID=?, COURSE_ID=?;";
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, enrollCount);
			statement.setInt(2, studentID);
			statement.setInt(3, courseID);
			enrollCount++;
		}
		try {
			statement.executeUpdate();
			jdbc_connection.commit();		// idk why this is needed but it just is and it took me a hour and a half to figure this out :(
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private ResultSet enrolled(int studentID, int courseID) {
		try {
			String sql = "SELECT * FROM " + tables[2] + " WHERE STUDENT_ID=? AND COURSE_ID=?";
			
			ResultSet set;
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, studentID);
			statement.setInt(2, courseID);
			//System.err.println(statement);
			set = statement.executeQuery();
			return set;
		} catch(Exception e) { return null; }
	}
	private int getRows(String table_name) {
		String sql = "SELECT COUNT(*) FROM " + table_name + ";";
		int set = 0;
		try {
			ResultSet setq = statement.executeQuery(sql);
			setq.next();
			set = setq.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	@Override
	public void toggleActive(int id) {
		String sql = "SELECT * FROM " + tables[1] + " WHERE ID=?";
		try {
		ResultSet set;
		statement = jdbc_connection.prepareStatement(sql);
		statement.setInt(1, id);
		//System.err.println(statement);
		set = statement.executeQuery();
		System.err.println(statement);
		set.next();
		boolean b  = set.getBoolean("ACTIVE");
		
		sql = "UPDATE " + tables[1] + " SET ACTIVE=? WHERE ID=?;";
		statement = jdbc_connection.prepareStatement(sql);
		statement.setBoolean(1, !b);
		statement.setInt(2, id);
		statement.executeUpdate();
		jdbc_connection.commit();
		System.err.println(statement);
		} catch (Exception e) {e.printStackTrace();}
		
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
