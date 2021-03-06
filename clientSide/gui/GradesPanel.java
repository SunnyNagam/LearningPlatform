/**
 * 
 */
package clientSide.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import format.Submission;
import format.Course;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class GradesPanel extends JPanel {
	private JButton newSubmission;
	public DefaultListModel<String> myGrades;
	public JList<String> gradeList;
	
	/**
	 * The JTitle
	 */
	public JLabel title;
	
	/**
	 * Constructs this panel
	 */
	public GradesPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		myGrades = new DefaultListModel<String>();
		
		gradeList = new JList<String> (myGrades);
		gradeList.setFont(new Font("menlo",Font.PLAIN,12));
		
		
		JPanel tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
		
		JScrollPane x = new JScrollPane(gradeList);
		//x.setPreferredSize(new Dimension(300,200));
		title = new JLabel("Assignment // Grades");
		
		tmp.add(title);
		tmp.add(x);
		add (tmp);
	}
	/**
	 * Initalizes jbuttons and actionlisteners
	 * @param a
	 */
	public void initialize(ActionListener a) {
		add( setup() );
		System.err.println("Creating grade Panel.");
		newSubmission = new JButton( "New Submission" );
		newSubmission.addActionListener(a);
		add( newSubmission, BorderLayout.SOUTH );
	}
	private JPanel setup() {
		JPanel tmp = new JPanel();
		tmp.add(new JLabel("Submitted By // Submission Date // Grade // submission ID"));
		JScrollPane x = new JScrollPane(gradeList);
		//x.setPreferredSize(new Dimension(300,200));
		tmp.add(x);
		return tmp;
	}
	
	/**
	 * Refreshes data
	 * @param set set to use (Arraylist)
	 */
	public void refreshData(ArrayList<String> set) {
		System.err.println("Refreshing grade data in gui");
		try {
			myGrades.removeAllElements();
			for(int x = 0; x < set.size(); x++) {
				myGrades.addElement(set.get(x));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}
	
	/**
	 * updates ui
	 */
	public void update() {
		gradeList.updateUI();
	}
}
