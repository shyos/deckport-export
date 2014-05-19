package extracter.GUI.WB;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import extracter.ExtracterMain;
import extracter.RobotManager;
import extracter.card.CardCount;
import extracter.card.Deck;
import extracter.card.DeckItem;

import java.awt.AWTException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;

public class ImportScreen extends JPanel {

	private JTextArea txtrJsonarea;
	/**
	 * Create the panel.
	 */
	public ImportScreen() {
		setLayout(null);
		
		txtrJsonarea = new JTextArea();
		txtrJsonarea.setText("Copy here");
		txtrJsonarea.setBounds(27, 15, 192, 23);
		add(txtrJsonarea);
		
		JButton btnImportAsJson = new JButton("Import As Json");
		btnImportAsJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(ExtracterMain.checkResolution())
				{
					Deck deck = null;
					boolean err = false;
					try {
						String jsonText = txtrJsonarea.getText();			
						Type mapType = new TypeToken<List<DeckItem>>(){}.getType(); 			
						ArrayList<DeckItem> cards = new Gson().fromJson(jsonText, mapType);
						deck = new Deck(cards, "Deck");
					} catch (JsonSyntaxException e1) {
						TrainingApp.showMessageDialog(null, "Json has errors, please copy from DeckLoad.com again!");
						err = true;
					}
					if(!err)
						try {
							RobotManager.importDeck(deck);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (AWTException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				}
			}
		});
		btnImportAsJson.setBounds(229, 11, 127, 23);
		add(btnImportAsJson);
		
		JTextPane txtpnWarning = new JTextPane();
		txtpnWarning.setText("What to do?\n\n"
				+ "1- Click 'Copy to Clipboard' button on DeckLoad for a deck\n"
				+ "2- Paste deck into above text area\n"
				+ "3- Click 'Import As Json'\n"
				+ "4- DON'T TOUCH MOUSE\n"
				+ "5- Wait until mouse stops.\n"
				+ "6- Done :)");
		txtpnWarning.setBounds(27, 96, 300, 149);
		add(txtpnWarning);

	}
}
