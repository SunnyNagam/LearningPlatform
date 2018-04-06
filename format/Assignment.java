package format;

import java.io.Serializable;
import java.util.ArrayList;

public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean active;
	private String name;
	private String path;
	private DropBox dropbox;
	
	public double getGradeFor(String name) {
		return dropbox.getGrade(name);
	}
}
