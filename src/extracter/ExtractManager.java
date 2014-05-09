package extracter;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ExtractManager {

	public static int findAverage(int order, BufferedImage image)
	{
		DeckItem card = new DeckItem(order);
		
		image = image.getSubimage(card.getImage().getX(),
								  card.getImage().getY(),
								  card.getImage().getW(),
								  card.getImage().getH()); //assign your bitmap here
	    int redColors = 0;
	    int greenColors = 0;
	    int blueColors = 0;
	    int pixelCount = 0;
	    int c = 0;
	    for (int y = 0; y < image.getHeight(); y++)
	    {
	        for (int x = 0; x < image.getWidth(); x++)
	        {
	            c += image.getRGB(x, y);
	            pixelCount++;

	        }
	    }
	    return c/pixelCount;
	}
}
