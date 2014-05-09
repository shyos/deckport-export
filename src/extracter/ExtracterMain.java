package extracter;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import extracter.card.Card;
import main.TesseractMain;
import main.WindowCapture;

public class ExtracterMain {
	public static ArrayList<Card> cards;
	public static Map<String, Card> cardMap;
	public static Map<Integer, Card> cardIdMap;
	public static ArrayList<Card> GUICards;
	public static WindowCapture WC;
	public static BufferedImage image;
	public static int numberOfCardInDeck = 20;
	
	public static void main(String[] args) throws IOException {
		
		buildEnvironment();   
	//	saveDeckManuel(image);		
		saveDeckAuto(image);	
	//	importNewCardsToOriginals("guicards.txt");

	}
	
	// Sets up default variables and build environment
	public static void buildEnvironment()
	{
		try {
			WC = new WindowCapture();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Gets Image
		image = WC.getImage();
		
		//Sets PixelManager with resolution
		PixelManager.setPixelManager();

		//Read card list from txt, build maps
		String cardsText = readFromFile("cards.txt");
		Type mapType = new TypeToken<List<Card>>(){}.getType(); 
		cards = new Gson().fromJson(cardsText, mapType);
		
		//Read GUI card list from txt
		String guicardsText = readFromFile("guicards.txt");
		GUICards =  new Gson().fromJson(guicardsText, mapType);
		
		cardMap = new HashMap<String, Card>();
		cardIdMap = new HashMap<Integer, Card>();
		for(Card card : cards)
		{
			cardMap.put(card.getName(), card);
			cardIdMap.put(card.getHearthhead_id(), card);
		}
	}
	
	// Finds similar of the card
	private static int matchCards(BufferedImage image) {
		return findSimilar(image);

	}
	// Compares current card with others (Algortihm included)
	private static int findSimilar(BufferedImage img1)
	{
	    int width1 = img1.getWidth(null);
	    int height1 = img1.getHeight(null);

	    int[][] imgRGB = new int [height1][width1];
	    for (int i = 0; i < height1; i++) {
		      for (int j = 0; j < width1; j++) {  
				imgRGB[i][j] = img1.getRGB(j, i);
		      }
		  }
	    double maxDiff = 100;
	    double diffPercent = 100;
	    int cardIndex = -1;
	    for(Card card : cards)
	    {
	    	if(card.getHash()!=null)
	    	{
			    long diff = 0;
			    
			    int[][] cardRGB = card.getHash();
			    for (int i = 0; i < height1; i++) {
			      for (int j = 0; j < width1; j++) {
			        int rgb1 = imgRGB[i][j];
			        int rgb2 = cardRGB[i][j];
			        int r1 = (rgb1 >> 16) & 0xff;
			        int g1 = (rgb1 >>  8) & 0xff;
			        int b1 = (rgb1      ) & 0xff;
			        int r2 = (rgb2 >> 16) & 0xff;
			        int g2 = (rgb2 >>  8) & 0xff;
			        int b2 = (rgb2      ) & 0xff;
			        diff += Math.abs(r1 - r2);
			        diff += Math.abs(g1 - g2);
			        diff += Math.abs(b1 - b2);
			      }
			    }
			    double n = width1 * height1 * 3;
			    double p = diff / n / 255.0;
			    diffPercent = (p * 100.0);
			    //System.out.println("diff percent: " + diffPercent);
			    if(diffPercent < maxDiff)
			    {
			    	maxDiff = diffPercent;
			    	cardIndex = card.getHearthhead_id();
			    	System.out.println("Possible: " + card.getName());
			    }
			    if(diffPercent < 2) break;
	    	}
	    }
	    System.out.println(cardIndex + " - " + diffPercent);
	    if(diffPercent < 25)
	    	return cardIndex;
	    else
	    	return -1;
	}
	
	// Deck Export
	private static void saveDeckAuto(BufferedImage image) {
		
		for(int i=0;i<numberOfCardInDeck;i++)
		{
			System.out.println("Giren Kart " + (i+1) );
			ExtractManager.cropImage(i, image);
			int cardIndex = matchCards(ExtractManager.subImage);
			if(cardIndex != -1)
			{
				System.out.println((i+1) + "/"+ numberOfCardInDeck + " - " + cardIdMap.get(cardIndex).getName());
			}
			else
			{
				System.out.println("Bu kart henüz sisteme tanýtýlmamýþ.");
			}
		}		
	}

	// Manuel Card Defining
	public static void saveDeckManuel(BufferedImage image) {

		Scanner scanIn = new Scanner(System.in);
 
		//Match Cards
		for(int k=0;k<numberOfCardInDeck;k++)
		{
			ExtractManager.cropImage(k, image);
			System.out.println((k+1) + ". kartin adini giriniz: ");
			String card_name = scanIn.nextLine();
			
			while(!cardMap.keySet().contains(card_name))
			{
				System.out.println((k+1) + ". kartin adini yanlis girdiniz lutfen tekrar giriniz: ");
				card_name = scanIn.nextLine();
			}
			
			if(cardMap.keySet().contains(card_name))
			{
				for(Card card : cards)
					if(card.getName().equals(card_name))
					{
						int[][] cardRGB = new int[PixelManager.getRgbH()][PixelManager.getRgbW()];
					
						for (int i = 0; i < PixelManager.getRgbH(); i++) {
						      for (int j = 0; j < PixelManager.getRgbW(); j++) {
						        cardRGB[i][j] = ExtractManager.subImage.getRGB(j, i);
						      }} 
						card.setHash(cardRGB);
						System.out.println((k+1) + ". kart icin islem basariyla tamamlanmistir.");
					}
			}	
		}
		//Write to a file
		writeToFile(cards, "cards.txt");
	}
	
	// Function which is only used by CrawlApp which is an GUI helps users to define cards
	public static int saveSingleCard(int k, String card_name)
	{
		ExtractManager.cropImage(k, image);
		System.out.println((k+1) + ". kartin adini giriniz: ");
		
		while(!cardMap.keySet().contains(card_name))
		{
			TesseractMain.showMessageDialog(null, (k+1) + ". kartin adini yanlis girdiniz lutfen tekrar giriniz! ");
			return k;
		}
		
		if(cardMap.keySet().contains(card_name))
		{
			for(Card card : cards)
				if(card.getName().equals(card_name))
				{
					int[][] cardRGB = new int[PixelManager.getRgbH()][PixelManager.getRgbW()];
				
					for (int i = 0; i < PixelManager.getRgbH(); i++) {
					      for (int j = 0; j < PixelManager.getRgbW(); j++) {
					        cardRGB[i][j] = ExtractManager.subImage.getRGB(j, i);
					      }} 
					card.setHash(cardRGB);
					Card tempCard = card;
					GUICards.add(tempCard);
					TesseractMain.showMessageDialog(null, (k+1) + ". kart icin islem basariyla tamamlanmistir.");
					System.out.println((k+1) + ". kart icin islem basariyla tamamlanmistir.");
					return k+1;
				}

		}
		return k;
	}

	// Writes cards to txt
	public static void writeToFile(ArrayList<Card> cards, String filename) {
		try {

			File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(new Gson().toJson(cards));
			bw.close();

 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Reads cards from txt
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

	// Import new data into cards.txt
	public static void importNewCardsToOriginals(String filename)
	{
		String cardsText = readFromFile(filename);
		Type mapType = new TypeToken<List<Card>>(){}.getType(); 
		ArrayList<Card> newCards = new Gson().fromJson(cardsText, mapType);
		
		cardsText = readFromFile("cards.txt");
		ArrayList<Card> originals = new Gson().fromJson(cardsText, mapType);
		HashMap<Integer, Card> originalsMap = new HashMap<Integer, Card>();
		for(Card card : originals)
		{
			originalsMap.put(card.getHearthhead_id(), card);
		}
		
		for(Card card : newCards)
		{
			originalsMap.put(card.getHearthhead_id(), card);
		}
		
		ArrayList<Card> resultList = new ArrayList<Card>();
		for(Integer key : originalsMap.keySet())
		{
			resultList.add(originalsMap.get(key));
		}
		
		writeToFile(resultList, "cards.txt");
	}
}
