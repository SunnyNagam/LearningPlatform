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
	public DefaultListModel<Submission> myGrades;
	public JList<Submission> gradeList;
	
	public JLabel title;
	
	public GradesPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		myGrades = new DefaultListModel<Submission>();
		
		gradeList = new JList<Submission> (myGrades);
		gradeList.setFont(new Font("menlo",Font.PLAIN,12));
		
		
		JPanel tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
		title = new JLabel("");
		tmp.add(title);
		add (tmp);
	}
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
	public void refreshData(ArrayList<Submission> set) {
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
	public void update() {
		gradeList.updateUI();
	}
}
