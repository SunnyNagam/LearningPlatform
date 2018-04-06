package format;

import java.nio.file.Path;
import java.time.LocalDate;

public class Submission {
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
