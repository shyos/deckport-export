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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import extracter.card.Card;
import main.WindowCapture;

public class ExtracterMain {

	public static void main(String[] args) throws IOException {
		
		//Captures hs screen
		WindowCapture WC = new WindowCapture();
		
		// Gets Image
		BufferedImage image = WC.getImage();
		
		//Sets PixelManager with resolution
		PixelManager.setPixelManager();

		//Read card list from txt 
		String cardsText = readFromFile();
		Type mapType = new TypeToken<List<Card>>(){}.getType(); 
		ArrayList<Card> cards = new Gson().fromJson(cardsText, mapType);
		Map<String, Card> cardMap = new HashMap<String, Card>();
		for(Card card : cards)
		{
			cardMap.put(card.getName(), card);
		}

		
		Scanner scanIn = new Scanner(System.in);
 
		
		for(int i=0;i<15;i++)
		{
			int value = ExtractManager.findAverage(i, image);
			
			System.out.println((i+1) + ". kartin adini giriniz: ");
			String card_name = scanIn.nextLine();
			
			while(!cardMap.keySet().contains(card_name))
			{
				System.out.println((i+1) + ". kartin adini yanlis girdiniz lutfen tekrar giriniz: ");
				card_name = scanIn.nextLine();
			}
			
			if(cardMap.keySet().contains(card_name))
			{
				for(Card card : cards)
					if(card.getName().equals(card_name))
					{
						card.setDeckImageHash(value);
						System.out.println((i+1) + ". kart icin islem basariyla tamamlanmistir.");
					}
			}
			
		}
		
		
		
		//Write to a file
		writeToFile(cards);
	}

	// Writes cards to txt
	private static void writeToFile(ArrayList<Card> cards) {
		try {
 
			File file = new File("cards.txt");
 
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
	private static String readFromFile() {
			 
		BufferedReader br = null;
		String cardsText = "";
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("cards.txt"));
 
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
