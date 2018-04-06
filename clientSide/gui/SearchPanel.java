package clientSide.gui;
/**
 * 
 */

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author keenangaudio
 *
 */
public class SearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final String ID_STRING = "ID number", NAME_STRING = "Name", TYPE_STRING = "Type";
	JPanel input;
	JComboBox<String> searchBy;
	JTextField key;
	public JButton searchButton;
	/**
	 * Constructor, setups up the custom panel that will handle search information from user
	 */
	public SearchPanel() {
		setBorder( BorderFactory.createTitledBorder("Search") );
		JPanel p = new JPanel();
		p.setLayout( new BoxLayout(p, BoxLayout.Y_AXIS) );
		p.add( setupInput() );
		p.add( buttonBox() );
		add(p);
	}
	private Component setupInput() {
		input = new JPanel();
		setupSearch();
		input.add( new JLabel("By ") );
		input.add( searchBy );
		input.add( new JLabel("For ") );
		input.add( key = new JTextField(13) );
		return input;
	}
	private Box buttonBox() {
		Box x = Box.createHorizontalBox();
		x.add(Box.createHorizontalGlue());
		x.add(searchButton = new JButton("Go!"));
		return x;
	}
	private void setupSearch() {
		String[] options = {ID_STRING, NAME_STRING};
		searchBy = new JComboBox<String>(options);
		searchBy.setSelectedIndex(0);
	}
	/**
	 * @return a string[] containing the two user input components
	 */
	String[] getStatus() {
		String[] str = new String[2];
		str[0] = (String) searchBy.getSelectedItem();
		str[1] = key.getText();
		return str;
	}
	/**
	 * @return the value from the JComboBox labeled 'By'
	 */
	public String by() {
		return (String) searchBy.getSelectedItem();
	}
	/**
	 * @return the value inputed into the text field labeled 'For'
	 */
	public String getKey() {
		return key.getText();
	}
}
