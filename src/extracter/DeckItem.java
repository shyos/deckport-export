package extracter;

import main.Myrect;

public class DeckItem {
	private Myrect mana;
	private Myrect text;
	private Myrect image;
	private Myrect count;
	private int baseY;
	public DeckItem(int order)
	{
		getCardByOrder(order);
		this.mana = (PixelManager.getMana());
		this.text = (PixelManager.getText());
		this.image = (PixelManager.getImage());
		this.count = (PixelManager.getCount());	
		setBaseYs();

	}
	private void setBaseYs() {
		// TODO Auto-generated method stub
		this.mana.setY(baseY);
		this.text.setY(baseY);
		this.image.setY(baseY);
		this.count.setY(baseY);
	}
	
	/**
	 * Re-calculates card's coordinates
	 * @param order
	 */
	public void getCardByOrder(int order)
	{
		this.baseY = PixelManager.getBaseY() + (PixelManager.getHeightOfACard() * order) + (PixelManager.getGap() * order);
		
	}
	public Myrect getMana() {
		return mana;
	}
	public void setMana(Myrect mana) {
		this.mana = mana;
	}
	public Myrect getText() {
		return text;
	}
	public void setText(Myrect text) {
		this.text = text;
	}
	public Myrect getImage() {
		return image;
	}
	public void setImage(Myrect image) {
		this.image = image;
	}
	public Myrect getCount() {
		return count;
	}
	public void setCount(Myrect count) {
		this.count = count;
	}
	public int getBaseY() {
		return baseY;
	}
	public void setBaseY(int baseY) {
		this.baseY = baseY;
	}
}
