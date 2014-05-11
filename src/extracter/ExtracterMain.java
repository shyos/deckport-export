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

import extracter.GUI.WB.TrainingApp;
import extracter.card.Card;
import extracter.card.Deck;
import extracter.card.DeckItem;
import main.TesseractMain;
import main.WindowCapture;

public class ExtracterMain {
	public static ArrayList<Card> cards;
	public static Map<String, Card> cardMap;
	public static Map<Integer, Card> cardIdMap;
	
	public static ArrayList<Card> GUICards;
	public static Map<Integer, Card> GUICardsIdMap;
	public static WindowCapture WC;
	public static BufferedImage image;
	public static int numberOfCardInDeck = 20;
	
	public static void main(String[] args) throws IOException {
		
		buildEnvironment();   
		saveDeckManuel(image);		
	//	saveDeckAuto(image);	
	//	importNewCardsToOriginals("guicards.txt");

	}
	
	public static Deck exportDeck(String deckName)
	{
	
		ArrayList<DeckItem> deckItems = new ArrayList<DeckItem>();
		
		buildEnvironment();
		deckItems = fetchCards(image);
		
		return new Deck(deckItems, deckName);
		
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

		//Read card list
		readCards();
		
		//Read GUI card list from txt
		readCardsForTraining();

	}

	//Read card list from txt, build maps
	private static void readCards()
	{
		String cardsText = readFromFile("cards.txt");
		Type mapType = new TypeToken<List<Card>>(){}.getType(); 
		cards = new Gson().fromJson(cardsText, mapType);
		
		cardMap = new HashMap<String, Card>();
		cardIdMap = new HashMap<Integer, Card>();
		for(Card card : cards)
		{
			cardMap.put(card.getName(), card);
			cardIdMap.put(card.getHearthhead_id(), card);
		}
	}
	
	//Read card list for Training App
	private static void readCardsForTraining()
	{
		String guicardsText = readFromFile("guicards.txt");
		Type mapType = new TypeToken<List<Card>>(){}.getType(); 
		GUICards =  new Gson().fromJson(guicardsText, mapType);
		GUICardsIdMap = new HashMap<Integer, Card>();
		for(Card card : GUICards)
		{
			GUICardsIdMap.put(card.getHearthhead_id(), card);
		}
	}
	
	// Finds similar of the card
	private static int matchCards(BufferedImage image) {
		return findSimilar(image);

	}
	// Compares current card with others (Algortihm included)
	private static int findSimilar(BufferedImage img)
	{
		return findSimilarWithIndex(img, 1);
	}
	
	// Sometimes images position changes few lines, to overcome this also check those coordinates.
	private static int findSimilarWithIndex(BufferedImage img1,int lineIndex) {
	    int width1 = img1.getWidth(null);
	    int height1 = img1.getHeight(null);

	    double maxDiff = 100;
	    double diffPercent = 100;
	    int cardIndex = -1;
	    
	    for(int h = 0; h<=lineIndex; h++)
	    {
		    int[][] imgRGB = new int [height1][width1];
		    for (int i = h; i < height1; i++) {
			      for (int j = 0; j < width1; j++) {  
					imgRGB[i-h][j] = img1.getRGB(j, i);
			      }
			 }
		
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
				    	System.out.println("Level: " + (h+1) + " Possible: " + card.getName() + " Similarity: " + diffPercent);
				    }
				    if(diffPercent < 2) break;
		    	}
		    	if(diffPercent < 2) break;
		    }
		    if(diffPercent < 2) break;
	    }
	    if(diffPercent < 25)
	    	return cardIndex;
	    else
	    	return -1;
	}

	// Deck Export
	private static ArrayList<DeckItem> fetchCards(BufferedImage image) {
		
		ArrayList<DeckItem> deckItems = new ArrayList<DeckItem>();
		for(int i=0;i<numberOfCardInDeck;i++)
		{
			System.out.println("Giren Kart " + (i+1) );
			ExtractManager.cropImage(i, image);
			int cardIndex = matchCards(ExtractManager.subImage);
			if(cardIndex != -1)
			{
				System.out.println((i+1) + "/"+ numberOfCardInDeck + " - " + cardIdMap.get(cardIndex).getName());
				deckItems.add(new DeckItem(cardIdMap.get(cardIndex)));
			}
			else
			{
				System.out.println("Bu kart henüz sisteme tanýtýlmamýþ.");
				deckItems.add(new DeckItem(new Card("UNKNOWN")));
			}
		}	
		return deckItems;
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

	// Used by TrainingAPP GUI to fetch card images
	public static void getCardImage(int k)
	{
		ExtractManager.cropImage(k, image);
	}
	
	// Used by TrainingAPP GUI to save single card hash (Only for Training Purposes)
	public static int saveSingleCard(int k, String card_name)
	{
		ExtractManager.cropImage(k, image);
		System.out.println((k+1) + ". kartin adini giriniz: ");
		
		while(!cardMap.keySet().contains(card_name))
		{
			TrainingApp.showMessageDialog(null, (k+1) + ". kartin adini yanlis girdiniz lutfen tekrar giriniz! ");
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
					
					if(!GUICardsIdMap.keySet().contains(tempCard.getHearthhead_id()))
						GUICards.add(tempCard);
					GUICardsIdMap.put(tempCard.getHearthhead_id(), tempCard);
					
					System.out.println((k+1) + ". kart icin islem basariyla tamamlanmistir.");
					return k+1;
				}

		}
		return k;
	}

	// Writes cards to txt
	public static void writeToFile(Object cards, String filename) {
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

	public static String getClientResolution() {
		return image.getWidth() + "x" + image.getHeight();
	}
}
