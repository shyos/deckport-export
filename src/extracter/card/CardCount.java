package extracter.card;

public class CardCount {
	public CardCount(int card_count, String card_count_type, int[][] countRGB) {
		this.description = card_count_type;
		this.count = card_count;
		this.h_1024x768 = countRGB;
	}
	private String description;
	private int count;
	private int[][] h_1024x768;
}
