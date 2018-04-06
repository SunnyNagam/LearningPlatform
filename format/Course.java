package format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Contains data and functionality for a course
 * @author sunnynagam, keenangaudio
 *
 */
public class Course {
	private int courseID;
	private String courseName;
	private String profName;
	private boolean active;
	private ArrayList<Assignment> assignment;
	private ChatRoom chatRoom;
	public Course(int id, String name, String prof, boolean a) {
		courseID = id;
		courseName = name;
		profName = prof;
		active = a;
	}
	//String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "PROF_ID INT(8) NOT NULL, "
	//+ "NAME VARCHAR(50) NOT NULL, " + "ACTIVE BIT(1) NOT NULL," + "PRIMARY KEY ( id ))";
	public static Course castRR(ResultSet r) {
		try {
			return new Course(r.getInt("ID"), r.getString("NAME"), r.getString("PROF_ID"), r.getBoolean("ACTIVE"));
		} catch (SQLException e) {
			return null;
		}
	}
	@Override 
	public String toString() {
		return String.format("%b%9s%51s", active, courseName, profName);
	}
}
