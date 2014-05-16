package extracter.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;


public class Deck {
	@Expose private String deckName;
	private String deckLink;
	private String deckOwner;
	@Expose private String deckClass;
	@Expose private List<DeckItem> cards;
	public Deck(ArrayList<DeckItem> deckItems, String deckName) {
		this.setCards(deckItems);
		this.deckName = deckName;
	}
	public String getDeckName() {
		return deckName;
	}
	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}
	public List<DeckItem> getCards() {
		return cards;
	}
	public void setCards(List<DeckItem> cards) {
		this.cards = cards;
	}
	public Object[] toArray(){
		Object[] myObject = new Object[cards.size()];
		int i=0;
		for(DeckItem d : cards)
		{
			myObject[i] = d.toString();
			i++;
		}
		return myObject; 
	}
	public String getDeckClass() {
		return deckClass;
	}
	public void setDeckClass(String deckClass) {
		this.deckClass = deckClass;
	}
}
