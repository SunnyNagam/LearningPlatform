package clientSide.gui;

import java.awt.BorderLayout;
import java.awt.Font;

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

		setLayout(new BorderLayout());
		
		submissions = new DefaultListModel<Submission>();
		
		submissionsList = new JList<Submission> (submissions);
		submissionsList.setFont(new Font("menlo",Font.PLAIN,12));
		
		JPanel tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
		tmp.add(new JLabel("Submitter // Date // Grade // Id"));
		JScrollPane x = new JScrollPane(submissionsList);
		//x.setPreferredSize(new Dimension(300,200));
		tmp.add(x);
		
		add(tmp);
		
		//add("North", new JLabel("Upload new document!"));
		add("Center", dispPanel);
		add("South", buttonPanel);
		
		//this.pack();
	}

	public DropBox getDbox() {
		return dbox;
	}

	public void setDbox(DropBox dbox) {
		this.dbox = dbox;
	}
	/**
	 * Initalizes submissons list
	 */
	public void init() {
		System.err.println("Adding submissions to gui.");
		try {
			submissions.removeAllElements();
			for(int x=0; x< dbox.getSubmissons().size(); x++) {
				Submission s = dbox.getSubmissons().get(x);
				submissions.addElement(s);
				System.err.println("Adding: "+submissions.get(x));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}

	public void update() {
		submissionsList.updateUI();
	}

	
}
