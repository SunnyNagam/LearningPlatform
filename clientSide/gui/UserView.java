package clientSide.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
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

	private JPanel outerPane;
	private JPanel contentPane;
	private LoginPanel loginPanel;
	private JPanel[] panels;
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
		
		outerPane = new JPanel();
		contentPane = new JPanel();
		/*						
		// commented out temporarily, idk why this is a popupmenu, but addit back later after login and make sure it's global
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
		outerPane.add(contentPane);
		add(outerPane);
		//panel.setLayout(new CardLayout());
		
	}

	public LoginPanel addLoginFunctionality() {
		LoginPanel pan = ((LoginPanel)loginPanel);
		return pan;
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

	public void switchWindow(int index) {
		if (index >= 0 && index < PanelList.ARRAY_SIZE) {
			contentPane = panels[index];
			System.out.println("Switched.");
		}
	}
	public void initializeView(String name) {
		menu = new JMenu(name.toUpperCase());
		setupMenu();
		outerPane.add(menu, BorderLayout.WEST);
	}
	private void setupMenu() {
		//add the buttony bois
	}
}
