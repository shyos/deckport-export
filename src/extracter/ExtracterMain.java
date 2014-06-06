package extracter;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import util.WindowCapture;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import extracter.GUI.WB.TrainingApp;
import extracter.card.Card;
import extracter.card.CardCount;
import extracter.card.Deck;
import extracter.card.DeckItem;
import extracter.card.prob.ProbList;
import extracter.card.prob.ProbListItem;

public class ExtracterMain {
	public static ArrayList<Card> cards;
	public static Map<String, Card> cardMap;
	public static Map<Integer, Card> cardIdMap;
	public static ArrayList<Card> GUICards;
	public static Map<Integer, Card> GUICardsIdMap;
	public static WindowCapture WC;
	public static BufferedImage image;
	public static int numberOfCardInDeck = 21;
	private static ArrayList<CardCount> cardCounts;
	public static ArrayList<ProbList> probList;
	public static void main(String[] args) throws IOException {
		
	//	buildEnvironment(); 
	//	readCards();
	//	saveDeckManuel(image);		
	//	saveDeckAuto(image);	
	//	importNewCardsToOriginals("guicards.txt");
	//	exportDeck("First");

	}
	
	// Returns Deck
	public static Deck exportDeck(String deckName)
	{
		ArrayList<DeckItem> deckItems = new ArrayList<DeckItem>();
		probList = new ArrayList<ProbList>();
		buildEnvironment();
		deckItems = fetchCards(image);
		boolean isScrollable = true;
		if(isScrollable)
		{
			ArrayList<DeckItem> scrolledDeckItems = new ArrayList<DeckItem>();
			RobotManager.scrollDown();
			WC.captureHwnd();
			image = WC.getImage();
			scrolledDeckItems = fetchCards(image);
			deckItems = mergeDeckParts(deckItems, scrolledDeckItems);
		}
		if(deckItems != null)
			return new Deck(deckItems, deckName);
		else return null;
	}
	
	// Merge two part of the deck. First part(without scroll) and second part(with scroll)
	private static ArrayList<DeckItem> mergeDeckParts(ArrayList<DeckItem> part1,
			ArrayList<DeckItem> part2) {
		
		ArrayList<ProbList> probListPart1 = new ArrayList<ProbList>();
		probListPart1.addAll(probList.subList(0, part1.size()));
		
	/*	for(ProbList y : probListPart1)
			System.out.println(y.getItems().getLast().getCard().getName() + " " + y.getProbMin());*/
		
		ArrayList<ProbList> probListPart2 = new ArrayList<ProbList>();
		probListPart2.addAll(probList.subList(part1.size(),probList.size()));
		
		//makeDeckLogical(part1,probListPart1);
		
		//TODO bos deck handle edilebilmeli
		//TODO yanlis kartlar duzeltilmeli
		//TODO 
		int j = 0;
		boolean onStreak = false;
		for(int i = 0; i<part1.size();i++)
		{
			// If two parts have shared cards skip them.
			Card c1 = part1.get(i).getCard();
			Card c2 = part2.get(j).getCard();
			if(part1.get(i).getCard().getHearthhead_id() == part2.get(j).getCard().getHearthhead_id())
			{
			//	System.out.println(part1.get(i).getCard().getName() + " is equal " + part2.get(j).getCard().getName());
				onStreak = true;
				j++;
			}
			else if(probListPart2.get(j).isInProbList(part1.get(i).getCard().getHearthhead_id()))
			{
				onStreak = true;
				j++;
			}
			else
			{
				if(onStreak == true) break;
			}
		}	
		// Add left cards
		for(;j<part2.size();j++)
		{
			part1.add(part2.get(j));
			probListPart1.add(probListPart2.get(j));
		}	
		// If deck reaches 30 card count, rip off rest
		int count = 0;
		int i;
		j=0;
		ArrayList<DeckItem> mergedDeck = new ArrayList<DeckItem>();
		ArrayList<ProbList> mergedProbList = new ArrayList<ProbList>();
		for(i=0;i<part1.size() && count<30;i++)
		{
			count+=part1.get(i).getCount();
			if(i!=0)
			{
				if(part1.get(i).getCard().getHearthhead_id() == mergedDeck.get(j-1).getCard().getHearthhead_id())
				{
					mergedDeck.get(j-1).setCount(mergedDeck.get(j-1).getCount()+1);
				}
				else
				{
					mergedDeck.add(part1.get(i));
					mergedProbList.add(probListPart1.get(i));
					j++;
				}
			}
			else
			{
				mergedDeck.add(part1.get(i));
				mergedProbList.add(probListPart1.get(i));
				j++;
			}
		}
		return makeDeckLogical(mergedDeck, mergedProbList);
	}


	private static ArrayList<DeckItem> makeDeckLogical(ArrayList<DeckItem> deck, ArrayList<ProbList> probList) {
		// TODO Auto-generated method stub
		int[] bankos = new int[deck.size()];
		int[] manas = new int[deck.size()];
		// Reset bankos
		for(int i = 0; i<bankos.length; i++)
		{
			bankos[i] = 0;
			manas[i] = 0;
		}
		// Find bankos level 1
		for(int i = 0; i<probList.size(); i++)
		{
			if(probList.get(i).getProbMin() < 6)
			{
				bankos[i] = 1;
				manas[i] = probList.get(i).getItems().getLast().getCard().getMana();
			}
		}
		// Find level 2
		for(int i=0;i<deck.size();i++)
		{
			if(bankos[i] == 0)
			{
				int lowM = findPrevMana(manas, i);
				int highM = findNextMana(manas, i);
				Card card = probList.get(i).getBestCardWithMana(lowM,highM);
				if(!card.getName().equals("UNKNOWN"))
				{
					bankos[i] = 1;
					manas[i] = card.getMana();
				}
				deck.get(i).setCard(card);
			}
		}
		
		//Invalid deck page
		int count = 0;
		for(int i=0; i<deck.size();i++)
		{
			count += deck.get(i).getCount();
		}
		
		if(count < 30)
			return null;
		if(deck.size() < 15)
			return null;
		
		return deck;
	}

	private static int findNextMana(int[] manas, int i) {
		for(int j=i;j<manas.length;j++)
			if(manas[j] > 0)
			{
				return manas[j];
			}
		return 20;
	}

	private static int findPrevMana(int[] manas, int i) {
		for(int j=i;j>=0;j--)
			if(manas[j] > 0)
			{
				return manas[j];
			}
		return 0;
	}

	// Sets up default variables and build environment
	public static void buildEnvironment()
	{
		//Capture window
		captureWindow();
	
		//Sets PixelManager with resolution
		PixelManager.setPixelManager();

		//Read card list
		readCards();
		
		//Read card count list
		readCardCounts();
		
		//Read GUI card list from txt
		//readCardsForTraining();

	}

	private static void captureWindow() {
		WC = new WindowCapture();
		// Gets Image
		image = WC.getImage();
	}

	private static void readCardCounts() {
		String guicardsText = readFromResourceFile("/txt/cardcounts.txt");
		Type mapType = new TypeToken<List<CardCount>>(){}.getType(); 
		cardCounts =  new Gson().fromJson(guicardsText, mapType);

	}

	//Read card list from txt, build maps
	private static void readCards()
	{
		String cardsText = readFromResourceFile("/txt/cards.txt");
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
	public static void readCardsForTraining()
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
	private static DeckItem matchCards(BufferedImage image, BufferedImage countImage, int manaFlag) {
		DeckItem deckItem = new DeckItem(findSimilarCard(image, manaFlag));
		deckItem.setCount(findSimilarCount(countImage));
		return deckItem;

	}
	// Compares current card with others (Algortihm included)
	private static Card findSimilarCard(BufferedImage img, int manaFlag)
	{
		return findSimilarCardWithIndex(img, 4, manaFlag);
	}
	// Compares current card count with others (Algortihm included)
	private static int findSimilarCount(BufferedImage img)
	{
		return findSimilarCountWithIndex(img, 4);
	}
	// Sometimes images position changes few lines, to overcome this also check those coordinates.
	private static Card findSimilarCardWithIndex(BufferedImage img1,int lineIndex, int manaFlag) {
	    int width1 = img1.getWidth(null);
	    int height1 = img1.getHeight(null);

	    double maxDiff = 100;
	    double diffPercent = 100;
	    ProbList pL = new ProbList();
	    Card returnCard = new Card("UNKNOWN");
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
		    	if(card.getHash()!=null) //&& card.getMana() >= manaFlag manaFlag condition removed
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
				    if(diffPercent < maxDiff)
				    {
				    	maxDiff = diffPercent;
				    	returnCard = card;
				    	//System.out.println("Level: " + (h+1) + " Possible: " + card.getName() + " Similarity: " + diffPercent);
				    }
				    if(diffPercent < pL.getProbMax())
				    {
				    	pL.add(card, diffPercent);
				    }
				    if(diffPercent < 2) break;
		    	}
		    	if(diffPercent < 2) break;
		    }
		    if(diffPercent < 2) break;
	    }
	    probList.add(pL);
	   // System.out.println(returnCard.getName());
	    return returnCard;
	}
	// Sometimes images position changes few lines, to overcome this also check those coordinates (for the card counts).
	private static int findSimilarCountWithIndex(BufferedImage img1,int lineIndex) {
	    int width1 = img1.getWidth(null);
	    int height1 = img1.getHeight(null);

	    double maxDiff = 100;
	    double diffPercent = 100;
	    int returnCount = 1;
	    for(int h = 0; h<=lineIndex; h++)
	    {
		    int[][] imgRGB = new int [height1][width1];
		    for (int i = h; i < height1; i++) {
			      for (int j = 0; j < width1; j++) {  
					imgRGB[i-h][j] = img1.getRGB(j, i);
			      }
			 }
		
		    for(CardCount cc : cardCounts)
		    {
		    	if(cc.getHash()!=null)
		    	{
				    long diff = 0;
				    
				    int[][] cardRGB = cc.getHash();
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
				    	returnCount = cc.getCount();
				    //	System.out.println("Level: " + (h+1) + " Possible: " + cc.getDescription() + " Similarity: " + diffPercent);
				    }
				    if(maxDiff < 2) break;
		    	}
		    	if(maxDiff < 2) break;
		    }
		    if(maxDiff < 2) break;
	    }
	    if(maxDiff < 7.5)
	    	return returnCount;
	    else 
	    	return 1;
	}
	
	// Deck Export
	private static ArrayList<DeckItem> fetchCards(BufferedImage image) {
		
		ArrayList<DeckItem> deckItems = new ArrayList<DeckItem>();
		int manaFlag = 0;
		for(int i=0;i<numberOfCardInDeck;i++)
		{
			ExtractManager.cropImage(i, image);
			DeckItem deckItem = matchCards(ExtractManager.subImage, ExtractManager.countImage, manaFlag);
			if(!deckItem.getCard().getName().equals("UNKNOWN"))
			{
				System.out.println((i+1) + "/"+ numberOfCardInDeck + " - " + deckItem.toString());
				deckItem.setImage(ExtractManager.deckItemImage);
				deckItems.add(deckItem);
			}
			else
			{
				System.out.println("Bu kart henüz sisteme tanýtýlmamýþ.");
				deckItems.add(deckItem);
			}
			manaFlag = deckItem.getCard().getMana();
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
		writeToResourceFile(cards, "/txt/cards.txt");
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

	// Writes cards to resource file as txt
	public static void writeToResourceFile(Object cards, String filename) {
		try {

			/*File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}*/
			PrintWriter writer = new PrintWriter(
		                     new File(ExtracterMain.class.getResource(filename).getPath()));
			
			writer.write(new Gson().toJson(cards));
			writer.close();

 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Reads cards from resource txt
	public static String readFromResourceFile(String filename) {
			 
		BufferedReader br = null;
		String cardsText = "";
		try {
			cardsText = IOUtils.toString(
				      ExtracterMain.class.getResourceAsStream(filename),
				      "UTF-8"
				    );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 

		return cardsText;
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
		
		cardsText = readFromResourceFile("/txt/cards.txt");
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
		
		writeToResourceFile(resultList, "/txt/cards.txt");
	}

	// Check client resolution
	public static boolean checkResolution() {
		ArrayList<String> availableRes = new ArrayList<String>();
		availableRes.add("1024x768");
		WindowCapture myWC = null;
		myWC = new WindowCapture();
		BufferedImage res = myWC.getImage();
		if(res == null)
		{
			TrainingApp.showMessageDialog(null, "Hearthstone.exe not found.");
			return false;
		}	
		Resolution clientRes = new Resolution(res.getWidth(), res.getHeight());
		if(availableRes.contains(clientRes.toString()))
		{
			Constants._RESOLUTION = clientRes;
			return true;
		}
		else
		{
			TrainingApp.showMessageDialog(null, "Your current resolution is "+ clientRes.toString() + ". Please change your Hearthstone resolution. Possible resolutions are: " + availableRes.toString());
			return false;
		}

	}
}
