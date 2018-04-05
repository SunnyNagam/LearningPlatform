package clientSide.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clientSide.Client;
import format.Communicate;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserView extends JFrame {

	private JPanel contentPane;
	private LoginPanel loginPanel;
	private JPanel viewPanel[];
	private JMenu menu;

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
		setBounds(100, 100, 450, 300);
		
		
		contentPane = new JPanel();
		/*						// commented out temporarily, idk why this is a popupmenu, but addit back later after login and make sure it's global
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.WEST);
		
		JMenu mnMenu = new JMenu("Menu");
		mnMenu.setFont(new Font("Apple Braille", Font.PLAIN, 14));
		scrollPane.setViewportView(mnMenu);
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem coursesButton = new JMenuItem("My Courses");
		popupMenu.add(coursesButton);
		addPopup(mnMenu, popupMenu);
		*/
		loginPanel = new LoginPanel();
		contentPane.add(loginPanel, BorderLayout.CENTER);
		add(contentPane);
		//panel.setLayout(new CardLayout());
		
	}

	public void addLoginFunctionality(Client c) {
		LoginPanel pan = ((LoginPanel)loginPanel);
		pan.submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = pan.userNameField.getText();
				String password = pan.passwordField.getText();
				
				c.writeTag(Communicate.LOGIN);
				try {
					c.write(Integer.parseInt(userName));
				}
				catch(Exception ex) {
					c.write(-1);
				}
				c.write(password);
				
				boolean success = c.readBoolean();
				
				if(!success) {
					displayErrorMessage("Invalid Credentials");
				}
				else {
					displayErrorMessage("YASSS");	// for now
					// TODO load user details and go to next panel
				}
				
			}
		});;
	}


	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	/**
	 * Displays error in prompt box
	 * 
	 * @param errorMessage
	 */
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}
	/**
	 * use for testing
	 * @param args - unused
	 */
	public static void main(String[] args) {
		UserView v = new UserView("TestView");
		
	}
}
