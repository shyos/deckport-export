package extracter.GUI.WB.topdeck;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import extracter.card.Deck;
import extracter.card.DeckItem;

public class CardCounterScreen extends JPanel {
	private  JTable table;

	/**
	 * Create the panel.
	 */
	public CardCounterScreen() {
		setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				null,
				new String[] {
					"New column", "New column", "New column", "New column"
				}
			));
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(116);
		table.getColumnModel().getColumn(2).setPreferredWidth(31);
		table.getColumnModel().getColumn(3).setPreferredWidth(42);
		table.setBounds(10, 11, 271, 406);
		add(table);

	}

	public CardCounterScreen(Deck deck) {
		this();
		ArrayList<DeckItem> deckItems = (ArrayList<DeckItem>) deck.getCards();
		Object[][] tableObject = new Object[deckItems.size()][4];
		for(int i=0; i<deckItems.size();i++){
			tableObject[i] = new Object[4];
			tableObject[i][0] = deckItems.get(i).getCard().getMana();
			tableObject[i][1] = deckItems.get(i).getCard().getName();
			tableObject[i][2] = deckItems.get(i).getCount();
			tableObject[i][3] = (double)deckItems.get(i).getCount()*10/3+"%";
		}
		table.setModel(new DefaultTableModel(
				tableObject,
				new String[] {
					"New column", "New column", "New column", "New column"
				}
			));


	}

}
