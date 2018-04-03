package format;

import java.time.LocalDate;
import java.util.ArrayList;

public class DropBox {
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
