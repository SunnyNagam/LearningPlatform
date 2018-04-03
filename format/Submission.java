package format;

import java.time.LocalDate;

public class Submission {
	private String submitter;
	private String submissionPath;
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
