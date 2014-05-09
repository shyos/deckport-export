package extracter;

import main.Myrect;

public class PixelManager {
	private static Myrect mana;
	private static Myrect text;
	private static Myrect image;
	private static Myrect count;
	private static int gap;
	private static int heightOfACard;
	private static int baseY;
	
	public static void setPixelManager(){
		initWithResolution();
	}
	
	// Fetches coordinates according to resolution
	private static void initWithResolution() {
		if(Constants._RESOLUTION.getHeight() == 768 && Constants._RESOLUTION.getWidth() == 1024)
		{
			mana = new Myrect(800,84,15,19);
			text = new Myrect(820,84,75,19);
			image = new Myrect(895,84,50,19);
			count = new Myrect(950,84,15,19);
			gap = 10;
			baseY = 84;
		}
		
	}

	public static Myrect getMana() {
		return mana;
	}

	public static void setMana(Myrect mana) {
		PixelManager.mana = mana;
	}

	public static Myrect getText() {
		return text;
	}

	public static void setText(Myrect text) {
		PixelManager.text = text;
	}

	public static Myrect getImage() {
		return image;
	}

	public static void setImage(Myrect image) {
		PixelManager.image = image;
	}

	public static Myrect getCount() {
		return count;
	}

	public static void setCount(Myrect count) {
		PixelManager.count = count;
	}

	public static int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		PixelManager.gap = gap;
	}

	public static int getHeightOfACard() {
		return heightOfACard;
	}

	public static void setHeightOfACard(int heightOfACard) {
		PixelManager.heightOfACard = heightOfACard;
	}

	public static int getBaseY() {
		return baseY;
	}

	public static void setBaseY(int baseY) {
		PixelManager.baseY = baseY;
	}


}
