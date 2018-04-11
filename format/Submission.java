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
	private int assign_id;
	private int submitter;
	private int course;
	private String submissionPath;
	private double grade;
	private String title;
	private LocalDate submissionDate;
	private Submission(int id, int assignID, int studID, int courseID, String path, String title, int grade, String date) {
		this.id = id;
		this.assign_id = assignID;
		this.submitter = studID;
		this.course = courseID;
		this.submissionPath = path;
		this.title = title;
		this.grade = grade;
		this.submissionDate = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	int getId() {
		return id;
	}
	int getAssign_id() {
		return assign_id;
	}
	int getSubmitter() {
		return submitter;
	}
	int getCourse() {
		return course;
	}
	String getSubmissionPath() {
		return submissionPath;
	}
	String getTitle() {
		return title;
	}
	LocalDate getSubmissionDate() {
		return submissionDate;
	}
	@Override 
	public String toString() {
		return String.format("%08d %10s %3.1f %8d", submitter, submissionDate, grade, id);
	}
//	String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, " + "ASSIGN_ID INT(8) NOT NULL, "
//			+ "STUDENT_ID INT(8) NOT NULL, " + "COURSE_ID INT(8) NOT NULL, " + "PATH VARCHAR(100) NOT NULL, "
//			+ "TITLE VARCHAR(50), " + "SUBMISSION_GRADE INT(3), " + "PRIMARY KEY ( id ))";
	public static Submission castRR(ResultSet r) {
		try {
			return new Submission(r.getInt("ID"),r.getInt("ASSIGN_ID"),r.getInt("STUDENT_ID"),r.getInt("COURSE_ID"), 
					r.getString("PATH"), r.getString("TITLE"), r.getInt("SUBMISSION_GRADE"), r.getString("DUE_DATE"));
		} catch (SQLException e) {return null;}
	}
	
}
