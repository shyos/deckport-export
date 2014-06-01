package extracter;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.WindowCapture;
import extracter.card.Card;
import extracter.card.CardCount;

public class CountManager {

	private static ArrayList<CardCount> cardCounts;
	private static BufferedImage subImage;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExtracterMain.buildEnvironment();
		readCardCounts();
		saveCardCountManuel(ExtracterMain.image);
		
	}
	public static void saveCardCountManuel(BufferedImage image) {
		Scanner scanIn = new Scanner(System.in);
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
			cardCounts.add(myCC);
		}
		//Write to a file
		ExtracterMain.writeToResourceFile(cardCounts, "/txt/cardcounts.txt");
	}
	public static void cropImage(int order, BufferedImage image)
	{
		DeckItemImage card = new DeckItemImage(order);
		
		image = image.getSubimage(card.getCount().getX(),
								  card.getCount().getY(),
								  card.getCount().getW(),
								  card.getCount().getH()); //crop image

	    subImage = image;

	    ExtracterMain.WC.showFrame(subImage);
	}
	private static void readCardCounts() {
		String cardcountsText = ExtracterMain.readFromResourceFile("/txt/cardcounts.txt");
		Type mapType = new TypeToken<List<CardCount>>(){}.getType(); 
		cardCounts =  new Gson().fromJson(cardcountsText, mapType);
		
	}
	private static String readFromFile(String filename) {
		 
		BufferedReader br = null;
		String cardsText = "";
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(filename));
 
			while ((sCurrentLine = br.readLine()) != null) {
				cardsText+=sCurrentLine;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return cardsText;
	}
}
