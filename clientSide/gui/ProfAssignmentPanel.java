/**
 * 
 */
package clientSide.gui;

import java.awt.Font;
import java.util.ArrayList;

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

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class ProfAssignmentPanel extends JPanel {
	private JButton addB;
	public DefaultListModel<Assignment> assignments;
	private JList<Assignment> assignmnetsList;
	
	//private JPanel display;
	
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
