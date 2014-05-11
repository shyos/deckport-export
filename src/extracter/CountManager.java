package extracter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

import main.WindowCapture;
import extracter.card.Card;
import extracter.card.CardCount;

public class CountManager {

	private static BufferedImage subImage;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExtracterMain.buildEnvironment();
		saveCardCountManuel(ExtracterMain.image);
	}
	public static void saveCardCountManuel(BufferedImage image) {

		Scanner scanIn = new Scanner(System.in);
 
		ArrayList<CardCount> ccList = new ArrayList<CardCount>();
		//Match Cards
		for(int k=0;k<2;k++)
		{
			cropImage(k, image);
			System.out.println((k+1) + ". kartin sayisini giriniz: ");
			int card_count = Integer.parseInt(scanIn.nextLine());
			System.out.println((k+1) + ". kartin sayisinin tipini giriniz (Golden/Normal): ");
			String card_count_type = scanIn.nextLine();
			
			int[][] countRGB = new int[subImage.getHeight()][subImage.getWidth()];
			for (int i = 0; i < subImage.getHeight(); i++) {
			      for (int j = 0; j < subImage.getWidth(); j++) {
			        countRGB[i][j] = subImage.getRGB(j, i);
			      }} 
			CardCount myCC = new CardCount(card_count, card_count_type, countRGB);
			ccList.add(myCC);
		}
		//Write to a file
		ExtracterMain.writeToFile(ccList, "cardcounts.txt");
	}
	public static void cropImage(int order, BufferedImage image)
	{
		DeckItemImage card = new DeckItemImage(order);
		
		image = image.getSubimage(card.getCount().getX(),
								  card.getCount().getY(),
								  card.getCount().getW(),
								  card.getCount().getH()); //crop image

	    subImage = image;
	  //  ExtracterMain.WC.showFrame(subImage);
	}
}
