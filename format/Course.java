package format;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Contains data and functionality for a course
 * @author sunnynagam, keenangaudio
 *
 */
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	private int courseID;
	public int getCourseID() {
		return courseID;
	}
	private String courseName;
	/**
	 * 
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * 
	 * @return profName
	 */
	public String getProfName() {
		return profName;
	}
	/**
	 * 
	 * @return active
	 */
	public boolean isActive() {
		return active;
	}
	private String profName;
	private boolean active;
	private ArrayList<Assignment> assignment;
	private ChatRoom chatRoom;
	/**
	 * creates a course with given params
	 * @param id
	 * @param name
	 * @param prof
	 * @param a
	 */
	public Course(int id, String name, String prof, boolean a) {
		courseID = id;
		courseName = name;
		profName = prof;
		active = a;
	}
	/**
	 * casts a result set into a course
	 * @param r a resultset
	 * @return a course
	 */
	public static Course castRR(ResultSet r) {
		try {
			return new Course(r.getInt("ID"), r.getString("NAME"), r.getString("PROF_ID"), r.getBoolean("ACTIVE"));
		} catch (SQLException e) {
			return null;
		}
	}
	/**
	 * @return a string representation of the class
	 */
	@Override 
	public String toString() {
		return String.format("%11s %51s %9s", (active?"Active":"Not Active"), courseName, profName);
	}
}
