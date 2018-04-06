package format;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class DropBox implements Serializable {
	private static final long serialVersionUID = 1L;
	private LocalDate dueTime;
	private ArrayList<Submission> submission;
	public ArrayList<Submission> getSubmissons() {
		return submission;
	}
	public double getGrade(String name) {
		for(int x=0; x<submission.size(); x++) {
			if(submission.get(x).getSubmitter().equals(name)) {
				return submission.get(x).getGrade();
			}
		}
		return -1;
	}
}
