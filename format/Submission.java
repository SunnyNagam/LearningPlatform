package format;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;

public class Submission implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String submitter;
	private Path submissionPath;
	private double grade;
	private LocalDate submissionDate;
	
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getSubmitter() {
		return submitter;
	}
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	
	@Override 
	public String toString() {
		return String.format("%11s %10s %5d %8d", submitter, submissionDate, grade, id);
	}
	
}
