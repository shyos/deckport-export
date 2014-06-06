package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class FileManager {
	
	// Write object into the file
	public static void writeToFile(Object object, String filename) {
		try {

			File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(new Gson().toJson(object));
			bw.close();

 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Reads object from file
	public static String readFromFile(String filename) {
			 
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

	public static boolean exists(String filename) {
		File folder = new File(filename);
		if(folder.exists())
			return true;
		return false;
	}
}
