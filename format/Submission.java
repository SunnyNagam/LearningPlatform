package format;

import java.io.Serializable;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Submission implements Serializable {
	private static final long serialVersionUID = 1L;
	public int id;
	public int assign_id;
	public int submitter;
	public int course;
	public String submissionPath;
	public int grade;
	public String title;
	public LocalDate submissionDate;
	/**
	 * constructor, assigns fields according to params
	 * @param id
	 * @param assignID
	 * @param studID
	 * @param courseID
	 * @param path
	 * @param title
	 * @param grade
	 * @param date
	 */
	public Submission(int id, int assignID, int studID, int courseID, String path, String title, int grade, String date) {
		this.id = id;
		this.assign_id = assignID;
		this.submitter = studID;
		this.course = courseID;
		this.submissionPath = path;
//		String[] p = path.split("/");
//		if (p.length > 0) this.submissionPath = p[p.length - 1];
		this.title = title;
		this.grade = grade;
		this.submissionDate = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
	}
	/**
	 * grade
	 * @return
	 */
	public double getGrade() {
		return grade;
	}
	/**
	 * @param grade
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}
	/**
	 * 
	 * @return id
	 */
	int getId() {
		return id;
	}
	/**
	 * 
	 * @return assign_id
	 */
	int getAssign_id() {
		return assign_id;
	}
	/**
	 * 
	 * @return submitter
	 */
	int getSubmitter() {
		return submitter;
	}
	/**
	 * 
	 * @return course
	 */
	int getCourse() {
		return course;
	}
	/**
	 * 
	 * @return submissionPath
	 */
	String getSubmissionPath() {
		return submissionPath;
	}
	/**
	 * 
	 * @return title
	 */
	String getTitle() {
		return title;
	}
	/**
	 * 
	 * @return submissionDate
	 */
	LocalDate getSubmissionDate() {
		return submissionDate;
	}
	/**
	 * @return String representation of the class
	 */
	@Override 
	public String toString() {
		return String.format("%08d %10s %3d %8d", submitter, submissionDate, grade, id);
	}
//	String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "ASSIGN_ID INT(8) NOT NULL, "
//			+ "STUDENT_ID INT(8) NOT NULL, " + "COURSE_ID INT(8) NOT NULL, " + "PATH VARCHAR(100) NOT NULL, "
//			+ "TITLE VARCHAR(50), " + "SUBMISSION_GRADE INT(3), " + "PRIMARY KEY ( id ))";
	/**
	 * 
	 * @param r
	 * @return
	 */
	public static Submission castRR(ResultSet r) {
		try {
			return new Submission(r.getInt("ID"),r.getInt("ASSIGN_ID"),r.getInt("STUDENT_ID"),r.getInt("COURSE_ID"), 
					r.getString("PATH"), r.getString("TITLE"), r.getInt("SUBMISSION_GRADE"), r.getString("DUE_DATE"));
		} catch (SQLException e) {return null;}
	}
	
}
