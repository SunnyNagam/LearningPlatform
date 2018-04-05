/**
 * 
 */
package clientSide.gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class LoginPanel extends JPanel {
	JTextField userNameField, passwordField;
	JLabel uNameLab, passLab, introLab;
	JButton submitButton;
	
	public LoginPanel() {
		userNameField = new JTextField(10);
		passwordField = new JTextField(10);
		uNameLab = new JLabel("UserName:");
		passLab = new JLabel("Password:");
		introLab = new JLabel("Login to D2L!");
		submitButton = new JButton("Submit");
		
		setLayout(new BorderLayout());
		
		JPanel logPan = new JPanel();
		logPan.add(uNameLab);
		logPan.add(userNameField);
		logPan.add(passLab);
		logPan.add(passwordField);
		
		add(logPan, BorderLayout.CENTER);
		
		JPanel topPan = new JPanel();
		topPan.add(introLab);
		
		add(topPan, BorderLayout.PAGE_START);
		
		JPanel botPan = new JPanel();
		botPan.add(submitButton);
		
		add(botPan, BorderLayout.PAGE_END);
	}
	public JButton getSub() {
		return submitButton;
	}
	public String getUser() {
		return userNameField.getText();
	}
	public String getPass() {
		return passwordField.getText();
	}
}
