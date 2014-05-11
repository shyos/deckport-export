package extracter;

import main.Myrect;

public class PixelManager {
	/*private static Myrect mana;
	private static Myrect text;
	private static Myrect image;
	private static Myrect count;*/
	
	private static int x_FirstCard;			// X of first card
	private static int y_FirstCard;			// Y of first card
	
	private static int x_Mana;				// X of Mana Segment
	private static int x_Text;				// X of Text Segment
	private static int x_Image;				// X of Image Segment
	private static int x_Count;				// X of Count Segment
	
	private static int w_Mana;				// W of Mana Segment
	private static int w_Text;				// W of Text Segment
	private static int w_Image;				// W of Image Segment
	private static int w_Count;				// W of Count Segment
	
	private static int cropHeight;			// Height of the parts that are cropped from both top and bottom of a card
	private static int heightOfACard;		// Height of a single card without crop
	
	private static int gap;					// Gap between two cards
	public static void setPixelManager(){
		initWithResolution();
	}
	
	// Fetches coordinates according to resolution
	private static void initWithResolution() {
		if(Constants._RESOLUTION.getHeight() == 768 && Constants._RESOLUTION.getWidth() == 1024)
		{
			x_FirstCard = 800;
			y_FirstCard = 80;
			
			x_Mana = 800;
			x_Text = 821;
			x_Image = 895;
			x_Count = 950;
			
			w_Mana = 15;
			w_Text = 90;
			w_Image = 50;
			w_Count = 12;
			
			gap = 1;
			heightOfACard = 28;
			cropHeight = 7;				
		}
		
	}

	public static Myrect getMana() {
		return new Myrect(x_Mana, getBaseY(), w_Mana, getBaseH());
	}

	public static Myrect getText() {

		return new Myrect(x_Text, getBaseY(), w_Text, getBaseH());
	}

	public static Myrect getImage() {
		return new Myrect(x_Image, getBaseY(), w_Image, getBaseH());
	}
	
	public static Myrect getCount() {
		return new Myrect(x_Count, getBaseY(), w_Count, getBaseH());
	}

	public static int getBaseY() {
		return y_FirstCard + cropHeight;
	}
	
	public static int getBaseH(){
		return heightOfACard - 2*cropHeight;
	}

	public static int getHeightOfACard() {
		return heightOfACard;
	}

	public static int getGap() {
		return gap;
	}

	public static int getRgbH() {
		return heightOfACard - 2*cropHeight;
	}

	public static int getRgbW() {
		return w_Text;
	}



}
