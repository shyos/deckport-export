package extracter.GUI.WB;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;

public class AboutScreen extends JPanel {

	/**
	 * Create the panel.
	 */
	public AboutScreen() {
		setLayout(null);
		
		JTextPane txtpnTwitter = new JTextPane();
		txtpnTwitter.setText(""
				+ "Web: DeckPort.com\n\n"
				+ "Twitter: twitter.com/decporths\n"
				+ "Facebook: facebook.com/deckport\n\n"
				+ "Github: github.com/shyos/deckport\n\n\n"
				+ "Deckport uses screen scrapping\n"
				+ "to execute all of its functions.\n"
				+ "No violation of ToS. Share and Enjoy!\n\n");
		txtpnTwitter.setEditable(false);
		txtpnTwitter.setBounds(104, 44, 219, 188);
		add(txtpnTwitter);

	}
}
