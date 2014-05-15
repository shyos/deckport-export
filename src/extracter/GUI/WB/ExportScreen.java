package extracter.GUI.WB;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;

import extracter.ExtracterMain;
import extracter.card.Deck;
import extracter.upload.Upload;

import java.awt.Font;

import javax.swing.JScrollBar;

import com.google.gson.Gson;

import java.awt.ScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

public class ExportScreen extends JPanel {
	private JTextField txtDeckname;
	private JList list;
	private JComboBox comboBox;
	private Deck deck;
	/**
	 * Create the panel.
	 */
	public ExportScreen() {
		setLayout(null);
		
		JButton btnNewButton = new JButton("Export Deck");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ExtracterMain.checkResolution())
				{
					deck = ExtracterMain.exportDeck(txtDeckname.getText());
					System.out.println(new Gson().toJson(deck));
					list.setListData(deck.toArray());
				}
			}
		});
		btnNewButton.setBounds(90, 14, 116, 25);
		add(btnNewButton);
		
		txtDeckname = new JTextField();
		txtDeckname.setText("deckName");
		txtDeckname.setBounds(90, 140, 116, 22);
		add(txtDeckname);
		txtDeckname.setColumns(10);
		
		JLabel lblDeckName = new JLabel("Deck Name :");
		lblDeckName.setBounds(10, 143, 92, 16);
		add(lblDeckName);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(216, 14, 173, 269);
		add(scrollPane);
		 
		  list = new JList();
		  scrollPane.setViewportView(list);
		  list.setFont(new Font("Tahoma", Font.PLAIN, 10));
		  
		  JButton btnPublishDeck = new JButton("Publish");
		  btnPublishDeck.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent arg0) {
		  		
		  		if(txtDeckname.getText().isEmpty())
		  			TrainingApp.showMessageDialog(null, "Deckname is empty.");
		  		else
		  		{
		  			deck.setDeckName(txtDeckname.getText());
		  			deck.setDeckClass(comboBox.getSelectedItem().toString());
			  		String result = Upload.publishDeck(deck);
			  		if(result.contains("http") && result.contains("deck"))
			  			TrainingApp.showMessageDialog(null, result);
			  		else
			  			TrainingApp.showMessageDialog(null, "Publish Failed");
		  		}
		  	}
		  });
		  btnPublishDeck.setBounds(90, 258, 116, 25);
		  add(btnPublishDeck);
		  
		  JLabel lblClass = new JLabel("Class :");
		  lblClass.setBounds(39, 170, 46, 14);
		  add(lblClass);
		  
		  comboBox = new JComboBox();
		  comboBox.setModel(new DefaultComboBoxModel(new String[] {"Mage", "Druid", "Warrior", "Warlock", "Priest", "Paladin", "Hunter", "Rogue", "Shaman"}));
		  comboBox.setBounds(90, 167, 116, 20);
		  add(comboBox);
		  
		  JSeparator separator = new JSeparator();
		  separator.setBounds(10, 50, 194, 8);
		  add(separator);
		  
		  JTextPane txtpnStep = new JTextPane();
		  txtpnStep.setFont(new Font("Tahoma", Font.PLAIN, 10));
		  txtpnStep.setText("Step 1:");
		  txtpnStep.setBounds(47, 19, 38, 20);
		  add(txtpnStep);
		  
		  JTextPane txtpnStep_1 = new JTextPane();
		  txtpnStep_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		  txtpnStep_1.setText("Step 2: "
		  		+ "Enter deckname and select a class.");
		  txtpnStep_1.setBounds(10, 110, 196, 25);
		  add(txtpnStep_1);
		  
		  JTextPane txtpnStep_2 = new JTextPane();
		  txtpnStep_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		  txtpnStep_2.setText("Step 3:");
		  txtpnStep_2.setBounds(47, 263, 41, 20);
		  add(txtpnStep_2);

	}
}
