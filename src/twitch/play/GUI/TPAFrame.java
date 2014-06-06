package twitch.play.GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import extracter.Constants;
import twitch.play.PlayConstants;
import twitch.play.PlayOptions;
import util.FileManager;

public class TPAFrame extends JFrame {

	public static JPanel contentPane;
	private SettingsScreen card1 = new SettingsScreen();
	public PollManagerScreen card2 = new PollManagerScreen();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TPAFrame frame = new TPAFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TPAFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(FileManager.exists(Constants.TPAFilename))
		{	
			String txt = FileManager.readFromFile(Constants.TPAFilename);
			java.lang.reflect.Type mapType = new TypeToken<PlayOptions>(){}.getType(); 			
			PlayOptions cons = new Gson().fromJson(txt, mapType);
			cons.setAll();
		}
		setBounds(100, 100, 480, 240);
		contentPane = new JPanel();
		contentPane.setLayout(new CardLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(card1, "HOME");
		contentPane.add(card2, "POLL");
	

		
	}

}
