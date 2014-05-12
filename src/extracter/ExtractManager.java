package extracter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.WindowCapture;

public class ExtractManager {

	public static BufferedImage subImage;		// Text Image of a DeckItem
	public static BufferedImage countImage;		// Count Image of a DeckItem
	
	public static void cropImage(int order, BufferedImage image)
	{
		DeckItemImage card = new DeckItemImage(order);
		
		subImage = image.getSubimage(card.getText().getX(),
								  card.getText().getY(),
								  card.getText().getW(),
								  card.getText().getH()); //crop image

	    countImage = image.getSubimage(card.getCount().getX(),
								  	   card.getCount().getY(),
								  	   card.getCount().getW(),
								  	   card.getCount().getH());
	  	    
	}

}
