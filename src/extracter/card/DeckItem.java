package extracter.card;

import com.google.gson.annotations.Expose;

public class DeckItem {
	@Expose private Card card;
	@Expose private int count;
	public DeckItem(Card myCard){
		this.card = myCard;
	}
	public DeckItem(Card myCard, int count)
	{
		this(myCard);
		this.count = count;
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
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
}
