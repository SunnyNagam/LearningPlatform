/**
 * 
 */
package clientSide.gui;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import format.Assignment;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class StudentAssignmentPanel extends JPanel {
	private JButton openB, submitB, downloadB;
	public DefaultListModel<Assignment> assignments;
	public JList<Assignment> assignmnetsList;
	public InsertView uploadPanel;
	public DropboxPanel dbView;
	//private JPanel display;
	/**
	 * initializes this panel
	 */
	public StudentAssignmentPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		assignments = new DefaultListModel<Assignment>();
		
		assignmnetsList = new JList<Assignment> (assignments);
		assignmnetsList.setFont(new Font("menlo",Font.PLAIN,12));
		
		JPanel tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
		tmp.add(new JLabel("Active // Name"));
		JScrollPane x = new JScrollPane(assignmnetsList);
		//x.setPreferredSize(new Dimension(300,200));
		tmp.add(x);
		
		add(tmp);
		add (addButtons());
		
		uploadPanel = new InsertView("Upload New Assignment");
		uploadPanel.setVisible(false);
	}
	/**
	 * @return jpanel with button in it
	 */
	private JPanel addButtons() {
		JPanel x = new JPanel();
		x.setLayout(new BoxLayout(x, BoxLayout.X_AXIS));
		
		x.add(Box.createHorizontalGlue());
		x.add(openB = new JButton("Open DropBox"));
		
		x.add(Box.createHorizontalGlue());
		x.add(downloadB = new JButton("Download Assignment File"));
		
		x.add(Box.createHorizontalGlue());
		x.add(submitB = new JButton("Submit to dropbox"));
		return x;
	}
	/**
	 * add action listeners
	 * @param a open button listener
	 * @param b download button listener
	 * @param c submit button listener
	 */
	public void addListen(ActionListener a, ActionListener b, ActionListener c) {
		openB.addActionListener(a);
		downloadB.addActionListener(c);
		submitB.addActionListener(b);
	}
	/**
	 * update UI
	 */
	public void update() {
		assignmnetsList.updateUI();
	}
	/**
	 * same as before, also I'm so tired
	 * @param set too tired to remember
	 */
	public void refreshData(ArrayList<Assignment> set) {
		System.err.println("Refreshing assignment data in gui");
		try {
			assignments.removeAllElements();
			for(int x=0; x< set.size(); x++) {
				Assignment assign = set.get(x);
				if (assign.active) assignments.addElement(assign);
				System.err.println("Adding: "+ assign);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
		
	}
	
}
