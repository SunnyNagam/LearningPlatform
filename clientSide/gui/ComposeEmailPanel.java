/**
 * 
 */
package clientSide.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author keenangaudio, sunnynagam
 *
 */
public class ComposeEmailPanel extends JPanel {
	JButton send;
	String[] labelText = {"To: ", "From: ", "Subject: "};
	JTextField[] text;
	JTextArea body;
	
	public ComposeEmailPanel() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		send = new JButton("Send");
		organize();
	}
	private void organize() {
		text = new JTextField[labelText.length];
		Box top = Box.createVerticalBox();
		for(int i = 0; i < labelText.length; i++) {
			Box tmp = Box.createHorizontalBox();
			//tmp.add(Box.createHorizontalGlue());
			tmp.add( new JLabel(labelText[i]) );
			tmp.add( text[i] = new JTextField() );
			text[i].setMaximumSize(new Dimension (500,20));
			//text[i].setMinimumSize(new Dimension (300,20));
			top.add(tmp);
			top.add(Box.createVerticalStrut(2));
		}
		add( top );
		add( body = new JTextArea() );
		Box b = Box.createHorizontalBox();
		b.add( Box.createHorizontalGlue() );
		b.add( send );
		add( b );
	}
	public void assignListener (ActionListener l) {
		send.addActionListener(l);
	}
	public String getTo() {
		return text[0].getText();
	}
	public String getFrom() {
		return text[1].getText();
	}
	public String getSubj() {
		return text[2].getText();
	}
	public String getBod() {
		return body.getText();
	}
}
