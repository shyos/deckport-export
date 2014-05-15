package extracter.GUI.WB;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;

public class HomeScreen extends JPanel {

	/**
	 * Create the panel.
	 */
	public HomeScreen() {
		setLayout(null);
		
		JTextPane txtpnHometext = new JTextPane();
		txtpnHometext.setBounds(33, 74, 327, 118);
		txtpnHometext.setText(""
				+ "How to use?\n"
				+ "- Export: You can export your decks and share it on DeckLoad.com\n"
				+ "- Import: You can copy decks from DeckLoad.com into Hearhstone\n"
				+ "\n"
				+ "All processes are automated. No hand-driven input.\n"
				+ "\n"
				+ "Coming Soon:\n"
				+ "- TopDeck calculator");
		add(txtpnHometext);
		
		JLabel lblDeckloadWindowsApp = new JLabel("DeckLoad Windows App");
		lblDeckloadWindowsApp.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		lblDeckloadWindowsApp.setBounds(96, 22, 180, 21);
		add(lblDeckloadWindowsApp);

	}
}
