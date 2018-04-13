package clientSide.gui;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import format.Communicate;
import format.Course;

import javax.swing.JSeparator;

import clientSide.User;

import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserView extends JFrame {

	private JPanel outerPane;
	private JPanel contentPane;
	public JLabel selectedCourse;
	private LoginPanel loginPanel;
	private JPanel[] panels;
	private int type;
	public int currentWindow = PanelList.MY_COURSES;
	
	public JPanel[] getPanels() {
		return panels;
	}

	private JButton[] menu;
	private ActionListener menuListener;
	public int[] keyEvents = {KeyEvent.VK_0,KeyEvent.VK_1,KeyEvent.VK_2,KeyEvent.VK_3,
			KeyEvent.VK_4,KeyEvent.VK_5,KeyEvent.VK_6,KeyEvent.VK_7,KeyEvent.VK_9};

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserView frame = new UserView(Communicate.PROGRAM_NAME);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public UserView(String s) {
		super(s);
		
		setBackground(new Color(204, 255, 153));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 600);
		
		outerPane = new JPanel();
		outerPane.setLayout(new BorderLayout());
		contentPane = new JPanel();

		loginPanel = new LoginPanel();
		contentPane.add(loginPanel, BorderLayout.CENTER);
		outerPane.add(contentPane);
		this.setTitle(PanelList.AT[PanelList.LOGIN]);

		add(outerPane);
		
		getRootPane().setDefaultButton(loginPanel.submitButton);
		
	}
	/**
	 * i want to die
	 * @return the login panel
	 */
	public LoginPanel getLoginPanel() {
		LoginPanel pan = ((LoginPanel)loginPanel);
		return pan;
	}


//	private static void addPopup(Component component, final JPopupMenu popup) {
//		component.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			public void mouseReleased(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			private void showMenu(MouseEvent e) {
//				popup.show(e.getComponent(), e.getX(), e.getY());
//			}
//		});
//	}
//	
	/**
	 * Displays error in prompt box
	 * 
	 * @param errorMessage
	 */
	public static void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage);
	}
	/**
	 * switches the window to the panel of index index
	 * @param index
	 */
	public void switchWindow(int index) {
		if (index >= 0 && index < PanelList.ARRAY_SIZE) {
			try {								
				outerPane.remove(contentPane);
			} catch (NullPointerException e) {}
			contentPane = panels[index];
			if (contentPane != null) outerPane.add (contentPane);
			
			if (index == PanelList.GRADES)
				this.setTitle(PanelList.AT[index].split(";")[(type == Communicate.PROFESSOR)?0:1]);
			else
				this.setTitle(PanelList.AT[index]);
			
			System.out.println("Switched to " + PanelList.AT[index]);
			currentWindow = index;
			this.paintAll(this.getGraphics());
		}
	}
	/**
	 * creates view panels, initializes shit i guess
	 * @param name
	 * @param type
	 */
	public void initializeView(String name, int type) {
		menu = new JButton[PanelList.ARRAY_SIZE];
		outerPane.add(setupMenu(name, type), BorderLayout.WEST);

		BorderLayout layout = (BorderLayout) outerPane.getLayout();

		outerPane.remove(layout.getLayoutComponent(BorderLayout.CENTER));

		contentPane = panels[PanelList.MY_COURSES];

		outerPane.add (contentPane);

	}
	private JPanel setupMenu(String name, int type) {
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout( new BoxLayout(menuPanel, BoxLayout.Y_AXIS) );
		menuPanel.setBackground( new Color(124, 112, 140) );
		menuPanel.add( selectedCourse = new JLabel("Select A Course") );
		menuPanel.add( new JSeparator() );
		setupMListener();
		createMenuButtons(type);
		
		for(JButton b : this.menu) if (b != null) {
			menuPanel.add( b );
			menuPanel.add(Box.createVerticalGlue());
		}
		return menuPanel;
	}
	/**
	 * this gives the menu button array, for like access or whatever
	 * @return
	 */
	public JButton[] getMenu() {
		return menu;
	}
	/**
	 * this imports the panel array
	 * @param p
	 */
	public void addPanels(JPanel[] p) {
		panels = new JPanel[p.length];
		for(int i = 0; i < p.length; i++)
			panels[i] = p[i];
	}
	/**
	 * this makes the buttons work
	 * @param type 
	 */
	private void createMenuButtons(int type) {
		//getRootPane().setDefaultButton(loginPanel.submitButton);
		instanciateButton(PanelList.MY_COURSES, type);
		menu[PanelList.MY_COURSES].setSelected(true);
	   
	    instanciateButton(PanelList.EMAIL_MAKER, type);
	    instanciateButton(PanelList.STUDENTS, type);
	    instanciateButton(PanelList.ASSIGNMENTS, type);
	    
	    ButtonGroup g = new ButtonGroup();
	    for(JButton b : menu) {
	    		if(b == null) continue; // not all panels are used?
	    		g.add( b );
	    		b.addActionListener(menuListener);
	    }
	}
	
	private void instanciateButton(int key, int type) {
		this.type = type;
		String label = PanelList.AT[key];
		if (key == PanelList.STUDENTS)
			label = PanelList.AT[key].split(";")[(type == Communicate.PROFESSOR)?0:1];
			
		menu[key] = new JButton(label);
		if (key <= 9) menu[key].setMnemonic(keyEvents[key]);
		menu[key].setActionCommand(PanelList.AT[key]);		//unshortened label
		menu[key].setSelected(false);
	}
	
	private void setupMListener() {
		menuListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int p = parse(e.getActionCommand());
				if (p != Communicate.INVALID) {
					switchWindow( p );
					unselectAll();
					menu[p].setSelected(true);
				}
			}
		};
	}
	
	protected void unselectAll() {
		for(JButton b : menu) {
    		if(b == null) continue; // not all panels are used?
    			b.setSelected(false);
		}
		
	}

	private int parse (String s) {
		for(int i = 0; i < PanelList.ARRAY_SIZE; i++)
			if (s.equals(PanelList.AT[i])) return i;
		return Communicate.INVALID;
	}
	
	private void updateCourses(ResultSet r) {
		try {
			while (r.next()) 
				((MyCoursesPanel)panels[PanelList.MY_COURSES]).myCourses.addElement(Course.castRR(r));
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
