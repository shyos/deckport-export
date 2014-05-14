package extracter.GUI.WB;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import extracter.RobotManager;
import extracter.card.CardCount;
import extracter.card.Deck;

import java.awt.AWTException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Type;
import java.util.List;

public class ImportScreen extends JPanel {

	private JTextArea txtrJsonarea;
	/**
	 * Create the panel.
	 */
	public ImportScreen() {
		setLayout(null);
		
		txtrJsonarea = new JTextArea();
		txtrJsonarea.setText("jsonArea");
		txtrJsonarea.setBounds(27, 15, 278, 23);
		add(txtrJsonarea);
		
		JButton btnImportAsJson = new JButton("Import As Json");
		btnImportAsJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String jsonText = txtrJsonarea.getText();
				Type mapType = new TypeToken<Deck>(){}.getType(); 
				Deck deck =  new Gson().fromJson(jsonText, mapType);
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
		});
		btnImportAsJson.setBounds(315, 15, 105, 23);
		add(btnImportAsJson);
		
		JButton btnImportFromSite = new JButton("Import As Link");
		btnImportFromSite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnImportFromSite.setBounds(315, 57, 105, 23);
		add(btnImportFromSite);
		
		JTextArea txtrLink = new JTextArea();
		txtrLink.setText("link");
		txtrLink.setBounds(27, 57, 278, 23);
		add(txtrLink);

	}
}
