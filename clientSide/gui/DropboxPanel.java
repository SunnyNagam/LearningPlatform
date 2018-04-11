package clientSide.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import clientSide.Controller;
import clientSide.Student;
import clientSide.User;
import format.Assignment;
import format.DropBox;
import format.Submission;
/**
 *  Dropbox window to view submissions to an assignment;
 * @author sunnynagam
 *
 */
public class DropboxPanel extends JPanel{

	private DropBox dbox;
	
	public DefaultListModel<Submission> submissions;
	public JList<Submission> submissionsList;
	
	public DropboxPanel(){
		JPanel buttonPanel = new JPanel ();
		JPanel dispPanel = new JPanel ();
		
		//JPanel titlePanel = new JPanel ();
		this.setSize(800, 500);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		submissions = new DefaultListModel<Submission>();
		
		submissionsList = new JList<Submission> (submissions);
		submissionsList.setFont(new Font("menlo",Font.PLAIN,12));
		//submissionsList.setBackground( new Color(142,214,021) );
		submissionsList.setVisibleRowCount(10);
		submissionsList.setFixedCellHeight(15);
		//submissionsList.setOpaque(true);
		
		JPanel tmp = new JPanel();
		dispPanel.setLayout(new BoxLayout(dispPanel, BoxLayout.Y_AXIS));
		//tmp.add(new JLabel("Submitter // Date // Grade // Id"));
		JScrollPane scroll = new JScrollPane(submissionsList);
		//x.setPreferredSize(new Dimension(300,200));
		
		dispPanel.add(new JLabel("Submitter // Date // Grade // Id"));
		dispPanel.add(scroll);
		
		//add(new JLabel("Assignment: "));
		//add(tmp);
		add(dispPanel);
		//add(buttonPanel);
		
	}

	public DropBox getDbox() {
		return dbox;
	}

	public void setDbox(DropBox dbox) {
		this.dbox = dbox;
	}
	
	public void refreshData(DropBox db, Controller c) {
		if (db == null) return;
		System.err.println("Refreshing dropbox data in gui");
		try {
			submissions.clear();
		} catch (Exception e) { e.printStackTrace(); }
		
		Iterator<Submission> i = db.getSubmissons().iterator();
		i.forEachRemaining( x -> {submissions.addElement(x); System.out.println(x);} );
		update();
		//System.out.println(submissionsList);
		submissionsList.setSelectedIndex(0);
	}
	/**
	 * Initalizes submissons list
	 */
//	public void init() {
//		System.err.println("Adding submissions to gui.");
//		try {
//			submissions.removeAllElements();
//			
//			for(int x=0; x< dbox.getSubmissons().size(); x++) {
//				Submission s = dbox.getSubmissons().get(x);
//				submissions.addElement(s);
//				System.err.println("Adding: "+submissions.get(x));
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		update();
//	}

	public void update() {
		//System.out.println(submissions.getSize());
		submissionsList.updateUI();
		//this.paintAll(this.getGraphics());
	}

	
}
