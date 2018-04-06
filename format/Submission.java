package format;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;

public class Submission implements Serializable {
	private static final long serialVersionUID = 1L;
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
	
}
