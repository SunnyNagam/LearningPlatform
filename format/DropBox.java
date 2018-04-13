package format;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class DropBox implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Submission> submission;
	
	public DropBox (ArrayList<Submission> subs) {
		submission = subs;
	}
	
	public DropBox () {
		 
	}
	/**
	 * @return all submissions in an arrayList
	 */
	public ArrayList<Submission> getSubmissons() {
		return submission;
	}
	/**
	 * returns grade of a specific students
	 * @param name
	 * @return
	 */
	public double getGrade(int name) {
		for(int x=0; x<submission.size(); x++) {
			if(submission.get(x).getSubmitter() == (name)) {
				return submission.get(x).getGrade();
			}
		}
		return -1;
	}
}
