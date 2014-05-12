package extracter.card;

import extracter.Constants;

public class CardCount {
	public CardCount(int card_count, String card_count_type, int[][] countRGB) {
		this.setDescription(card_count_type);
		this.setCount(card_count);
		this.h_1024x768 = countRGB;
	}
	public CardCount() {
		this.setCount(1);
	}
	private String description;
	private int count;
	private int[][] h_1024x768;
	public int[][] getHash() {
		if(Constants._RESOLUTION.getHeight() == 768 && Constants._RESOLUTION.getWidth() == 1024)
		{
			if(h_1024x768 != null)
				return h_1024x768;
			else
				return null;
		}
		return null;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
