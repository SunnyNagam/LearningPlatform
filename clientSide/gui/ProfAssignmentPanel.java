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

import clientSide.Student;
import clientSide.User;
import format.Assignment;
import format.DropBox;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class ProfAssignmentPanel extends JPanel {
	private JButton addB, toggleB, openB;
	public DefaultListModel<Assignment> assignments;
	public JList<Assignment> assignmnetsList;
	public InsertView uploadPanel;
	public DropboxPanel dbView;
	//private JPanel display;
	
	/**
	 * Constructs prof assignmetn panel
	 */
	public ProfAssignmentPanel() {
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
	 * Adds buttons
	 * @return
	 */
	private JPanel addButtons() {
		JPanel x = new JPanel();
		x.setLayout(new BoxLayout(x, BoxLayout.X_AXIS));
		
		x.add(Box.createHorizontalGlue());
		x.add(addB = new JButton("Upload"));
		
		x.add(Box.createHorizontalGlue());
		x.add(toggleB = new JButton("Toggle Active"));
		
		x.add(Box.createHorizontalGlue());
		x.add(openB = new JButton("Open DropBox"));
		return x;
	}
	
	/**
	 * Adds listeners
	 * @param a listener
	 * @param c listener
	 * @param b listener
	 */
	public void addListen(ActionListener a, ActionListener c, ActionListener b) {
		addB.addActionListener(a);
		openB.addActionListener(c);
		toggleB.addActionListener(b);
	}
	/**
	 * Updates ui
	 */
	public void update() {
		assignmnetsList.updateUI();
	}
	
	/**
	 * Refreshes data in internal jlists
	 * @param set data to use
	 */
	public void refreshData(ArrayList<Assignment> set) {
		System.err.println("Refreshing assignment data in gui");
		try {
			assignments.removeAllElements();
			for(int x=0; x< set.size(); x++) {
				Assignment assign = set.get(x);
				assignments.addElement(assign);
				System.err.println("Adding: "+assignments.get(x));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		update();
		
	}
	
}
