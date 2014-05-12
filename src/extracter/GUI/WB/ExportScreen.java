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
					Deck deck = ExtracterMain.exportDeck(txtDeckname.getText());
					System.out.println(new Gson().toJson(deck));
					list.setListData(deck.toArray());
				}
			}
		});
		btnNewButton.setBounds(260, 13, 131, 25);
		add(btnNewButton);
		
		txtDeckname = new JTextField();
		txtDeckname.setText("deckName");
		txtDeckname.setBounds(127, 14, 116, 22);
		add(txtDeckname);
		txtDeckname.setColumns(10);
		
		JLabel lblDeckName = new JLabel("Deck Name :");
		lblDeckName.setBounds(34, 17, 92, 16);
		add(lblDeckName);
		
		 list = new JList();
		 list.setFont(new Font("Tahoma", Font.PLAIN, 10));
		list.setBounds(22, 53, 198, 209);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 53, 198, 209);
		scrollPane.setViewportView(list);
		add(scrollPane);

	}
}
