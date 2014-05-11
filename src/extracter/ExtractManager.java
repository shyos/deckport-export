package extracter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.WindowCapture;

public class ExtractManager {

	public static BufferedImage subImage;
	
	public static void cropImage(int order, BufferedImage image)
	{
		DeckItemImage card = new DeckItemImage(order);
		
		image = image.getSubimage(card.getText().getX(),
								  card.getText().getY(),
								  card.getText().getW(),
								  card.getText().getH()); //crop image

	    subImage = image;
	}

}
