/**
 * 
 */
package clientSide.gui;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	private JButton openB, submitB;
	public DefaultListModel<Assignment> assignments;
	public JList<Assignment> assignmnetsList;
	public InsertView uploadPanel;
	public DropboxPanel dbView;
	//private JPanel display;
	
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
	private JPanel addButtons() {
		JPanel x = new JPanel();
		x.setLayout(new BoxLayout(x, BoxLayout.X_AXIS));
		
		x.add(Box.createHorizontalGlue());
		x.add(openB = new JButton("Open DropBox"));
		
		x.add(Box.createHorizontalGlue());
		x.add(submitB = new JButton("Submit to dropbox"));
		return x;
	}
	public void addListen(ActionListener a, ActionListener b) {
		openB.addActionListener(a);
		submitB.addActionListener(b);
	}
	public void update() {
		assignmnetsList.updateUI();
	}
	
	public void refreshData(ArrayList<Assignment> set) {
		System.err.println("Refreshing assignment data in gui");
		try {
			assignments.removeAllElements();
			for(int x=0; x< set.size(); x++) {
				Assignment assign = set.get(x);
				if (assign.active) assignments.addElement(assign);
				System.err.println("Adding: "+assignments.get(x));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
		
	}
	
}
