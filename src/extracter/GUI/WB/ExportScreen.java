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

import java.awt.Font;

import javax.swing.JScrollBar;

import com.google.gson.Gson;

import java.awt.ScrollPane;

public class ExportScreen extends JPanel {
	private JTextField txtDeckname;
	private JList list;
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
		btnNewButton.setBounds(88, 55, 116, 25);
		add(btnNewButton);
		
		txtDeckname = new JTextField();
		txtDeckname.setText("deckName");
		txtDeckname.setBounds(88, 14, 116, 22);
		add(txtDeckname);
		txtDeckname.setColumns(10);
		
		JLabel lblDeckName = new JLabel("Deck Name :");
		lblDeckName.setBounds(10, 17, 92, 16);
		add(lblDeckName);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(230, 14, 198, 269);
		add(scrollPane);
		 
		  list = new JList();
		  scrollPane.setViewportView(list);
		  list.setFont(new Font("Tahoma", Font.PLAIN, 10));
		  
		  JButton btnPublishDeck = new JButton("Publish");
		  btnPublishDeck.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent arg0) {
		  		
		  	}
		  });
		  btnPublishDeck.setBounds(88, 258, 116, 25);
		  add(btnPublishDeck);
		  
		  JButton btnCopyToClipboard = new JButton("Copy Deck");
		  btnCopyToClipboard.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  	}
		  });
		  btnCopyToClipboard.setBounds(88, 222, 116, 25);
		  add(btnCopyToClipboard);

	}
}
