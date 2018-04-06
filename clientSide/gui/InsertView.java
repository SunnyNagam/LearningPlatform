package clientSide.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/**
 * Insert view class for window to insert new assignment into tree
 * @author sunnynagam
 *
 */
public class InsertView extends JFrame{

	/**
	 * Text fields to enter info about new assignment to insert
	 */
	public JTextField titletxt = new JTextField(10), pathtxt = new JTextField(10),
			duetxt = new JTextField(10);
	
	public JCheckBox activeBox = new JCheckBox("Active");
	
	/**
	 * Buttons to insert new student or quit
	 */
	public JButton insertB = new JButton ("Upload"), returnB = new JButton ("Return to Main Window");
	
	/**
	 * Title prompting new student
	 */
	private JLabel titleL = new JLabel("Assignment Title");
	
	/**
	 * Student prompt
	 */
	private JLabel pathL = new JLabel("Path");
	/**
	 * Faculty prompt
	 */
	private JLabel dueL = new JLabel("Due Date");
	/**
	 * Major prompt
	 */
	private JLabel activeL = new JLabel("Active");
    
	public InsertView(String s){
		super(s);
		JPanel buttonPanel = new JPanel ();
		JPanel dispPanel = new JPanel ();
		//JPanel titlePanel = new JPanel ();
		this.setSize(500, 150);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		dispPanel.add(titleL);
		dispPanel.add(titletxt);
		
		dispPanel.add(pathL);
		dispPanel.add(pathtxt);
		
		dispPanel.add(dueL);
		dispPanel.add(duetxt);
		
		dispPanel.add(activeL);
		dispPanel.add(activeBox);
		
		
		buttonPanel.add(insertB);
		buttonPanel.add(returnB);
		
		//title.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		setLayout(new BorderLayout());
		
		add("North", new JLabel("Upload new document!"));
		add("Center", dispPanel);
		add("South", buttonPanel);
		
		//this.pack();
	}

	/**
	 * Clears values of all text boxes
	 */
	public void clearInput() {
		pathtxt.setText("");
		duetxt.setText("");
		titletxt.setText("");
		activeBox.setSelected(false);
	}
	
}
