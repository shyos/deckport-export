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
	private static int baseH;
	private static int rgbW;
	private static int rgbH;
	public static void setPixelManager(){
		initWithResolution();
	}
	
	// Fetches coordinates according to resolution
	private static void initWithResolution() {
		if(Constants._RESOLUTION.getHeight() == 768 && Constants._RESOLUTION.getWidth() == 1024)
		{
			baseY = 87;
			baseH = 13;
			mana = new Myrect(800,baseY,15,baseH);
			text = new Myrect(820,baseY,45 + (45)*Constants.isBig,baseH);
			image = new Myrect(895,baseY,50,baseH);
			count = new Myrect(950,baseY,15,baseH);
			gap = 10;
			heightOfACard = 19;
			rgbW = 45 + (45)*Constants.isBig;
			rgbH = 13;
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

	/**
	 * @return the baseH
	 */
	public static int getBaseH() {
		return baseH;
	}

	/**
	 * @param baseH the baseH to set
	 */
	public static void setBaseH(int baseH) {
		PixelManager.baseH = baseH;
	}

	public static int getRgbW() {
		return rgbW;
	}

	public static void setRgbW(int rgbW) {
		PixelManager.rgbW = rgbW;
	}

	public static int getRgbH() {
		return rgbH;
	}

	public static void setRgbH(int rgbH) {
		PixelManager.rgbH = rgbH;
	}


}
