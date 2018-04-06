package format;

import java.io.Serializable;
import java.util.ArrayList;

public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean active;
	private String name;
	private String path;
	public int courseID, id;
	private DropBox dropbox;
	
	public double getGradeFor(String name) {
		return dropbox.getGrade(name);
	}
	public Assignment(String name, String path, boolean active, int courseID, int id) {
		this.name = name;
		this.path = path;
		this.courseID = courseID;
		this.id = id;
		this.active = active;
		dropbox = new DropBox();
	}
	@Override 
	public String toString() {
		return String.format("%11s %51s %8d", (active?"Active":"Not Active"), name, id);
	}
}
