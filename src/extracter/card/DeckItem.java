package extracter.card;

public class DeckItem {
	private Card card;
	private int count = 1;
	public DeckItem(Card card){
		this.setCard(card);
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String toString()
	{
		return card.getName() + " x" + count; 
	}
}
