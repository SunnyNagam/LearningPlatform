package format;

import java.util.ArrayList;

public class Assignment {
	private boolean active;
	private String name;
	private String path;
	private DropBox dropbox;
	
	public double getGradeFor(String name) {
		return dropbox.getGrade(name);
	}
}
