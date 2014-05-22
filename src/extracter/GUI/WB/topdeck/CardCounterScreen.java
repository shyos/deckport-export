package extracter.GUI.WB.topdeck;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import extracter.card.Deck;
import extracter.card.DeckItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardCounterScreen extends JPanel {
	private  JTable table;
	private Object[][] tableObject;
	private int numberOfCardsLeft = 30;
	private ArrayList<DeckItem> deckItems;
	/**
	 * Create the panel.
	 */
	public void init()
	{
		table = new JTable();
		table.setEnabled(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3)
				{
					int r = table.rowAtPoint(e.getPoint());
			    	if((Integer) tableObject[r][2] < deckItems.get(r).getCount() && (Integer) tableObject[r][2]>=0)
			    	{
			    		tableObject[r][2]= (Integer) tableObject[r][2] +1;
			 			numberOfCardsLeft +=1;
				    	reCalculateTD();
				    	setTableModel();
			    	}
				}
			    if(e.getButton() == MouseEvent.BUTTON1)
			    {
			    	int r = table.rowAtPoint(e.getPoint());
			    	if((Integer) tableObject[r][2] <= deckItems.get(r).getCount() && (Integer) tableObject[r][2]>0)
			    	{
			    		tableObject[r][2]= (Integer) tableObject[r][2] -1;
			 			numberOfCardsLeft -=1;
				    	reCalculateTD();
				    	setTableModel();
			    	}
			    }
			}

			private void reCalculateTD() {
				for(int i=0;i<tableObject.length;i++)
				{
					tableObject[i][3] = String.format( "%.2f", (double)(Integer)tableObject[i][2]*100/numberOfCardsLeft );
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"Mana", "Name", "Count", "TD"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(18);
		table.getColumnModel().getColumn(1).setPreferredWidth(161);
		table.getColumnModel().getColumn(2).setPreferredWidth(17);
		table.getColumnModel().getColumn(3).setPreferredWidth(37);
		table.setBounds(10, 11, 271, 406);
		add(table);
	}
	public CardCounterScreen(Deck deck) {
		init();
		if(deck!=null)
		{
			deckItems = (ArrayList<DeckItem>) deck.getCards();
			tableObject = new Object[deckItems.size()][4];
			for(int i=0; i<deckItems.size();i++){
				tableObject[i] = new Object[4];
				tableObject[i][0] = deckItems.get(i).getCard().getMana();
				tableObject[i][1] = deckItems.get(i).getCard().getName();
				tableObject[i][2] = deckItems.get(i).getCount();
				tableObject[i][3] = String.format( "%.2f", (double)deckItems.get(i).getCount()*100/numberOfCardsLeft );
			}
			setTableModel();

		}
	}
	public void setTableModel()
	{
		table.setModel(new DefaultTableModel(
				tableObject,
				new String[] {
					"Mana", "Name", "Count", "TD"
				}
			));
		table.getColumnModel().getColumn(0).setPreferredWidth(18);
		table.getColumnModel().getColumn(1).setPreferredWidth(161);
		table.getColumnModel().getColumn(2).setPreferredWidth(17);
		table.getColumnModel().getColumn(3).setPreferredWidth(37);

	}

}
