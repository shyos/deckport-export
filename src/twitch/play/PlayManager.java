package twitch.play;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import twitch.chat.ChatMessage;
import twitch.chat.ChatParser;
import twitch.chat.IRCConnection;
import twitch.play.GUI.PollManagerScreen;
import twitch.play.GUI.PollOverlayScreen;
import extracter.PixelManager;
import extracter.RobotManager;

public class PlayManager {
    // Connection infos
	public static String user = PlayConstants.user;
	public static String pass = PlayConstants.oAuth;
    
    // Connection objects
	static BufferedReader reader;
	static BufferedWriter writer;
	static IRCConnection irc;
	
	// Choice Options
	static String _OPTION_1= "!1";
	static String _OPTION_2= "!2";
	static String _OPTION_3= "!3";
	
	// User choice count
	static int _COUNT_1 = 0;
	static int _COUNT_2 = 0;
	static int _COUNT_3 = 0;
	
	// Bot Commands
	public static String startClassPoll = "!startarenaclasspick";
	public static String endClassPoll = "!endarenaclasspick";
	public static String startCardPoll = "!startcardpick";
	public static String endCardPoll= "!endcardpick";
	
	// Bot Options
	public static int currentCardIndex = 1;
	static boolean allowMultipleVotes = false;
	boolean allowVoteChanges = false;
	static boolean isPollOn = false;
	
	// Bot Timer Options (in seconds)
	public static int classTimer = PlayConstants.classDelay;
	public static int cardTimer = PlayConstants.cardDelay;
	public static int twitchDelay = PlayConstants.twitchDelay;
	
	static Map<String,String> votes = new HashMap<String,String>();
	private static int pollMaxResult = 0;
	
	public static void main(String[] args) throws IOException {
		// Create connection to IRC, build streams
		setupEnvironment();
				
		ChatMessage msg;
		String line = null;
		while((line =  reader.readLine( )) != null) {
	        if (line.startsWith("PING ")) {
	            // We must respond to PINGs to avoid being disconnected.
	        	writer.write("PONG " + line.substring(5) + "\r\n");
	            writer.flush( );
	        }
	        else {
	            msg = ChatParser.parse(line);
	            //System.out.println(line);
	            if(msg!=null)
	            {
	        		StringWriter text = new StringWriter();
	                PrintWriter out = new PrintWriter(text);
	                out.println(PollManagerScreen.txtrTextArea.getText());
	                out.printf(msg.toString());
	                PollManagerScreen.txtrTextArea.setText(text.toString());
	            	processMessage(msg);
	            }
	        } 
	    }
		
	}
	
	private static void processMessage(ChatMessage msg) {
		// Admin/Bot commands
		if(msg.getUser().equalsIgnoreCase(user))
		{
			if(msg.getText().equalsIgnoreCase(startClassPoll))
			{
				reset();
			}
			if(msg.getText().equalsIgnoreCase(endClassPoll))
			{
				System.out.println("Option:" + findMax() + " with " + pollMaxResult + " votes out of " + (_COUNT_1+_COUNT_2+_COUNT_3));
				processClassOption(findMax());
				reset();
			}
			if(msg.getText().equalsIgnoreCase(startCardPoll + currentCardIndex))
			{
				reset();
			}
			if(msg.getText().equalsIgnoreCase(endCardPoll + currentCardIndex))
			{
				System.out.println("Card Order:" + currentCardIndex + "Option:" + findMax() + " with " + pollMaxResult + " votes out of " + (_COUNT_1+_COUNT_2+_COUNT_3));
				processCardOption(findMax());
				reset();
				currentCardIndex++;
			}
		}

		if(!isPollOn)
		{
			
		}
		else
		{
			// If user didnt cast his vote yet, let him vote
			if(!votes.keySet().contains(msg.getUser()))
			{
				if(msg.getText().startsWith(_OPTION_1))
				{
					votes.put(msg.getUser(), _OPTION_1);
					set_COUNT_1(++_COUNT_1);
				}
				if(msg.getText().startsWith(_OPTION_2))
				{
					votes.put(msg.getUser(), _OPTION_2);
					set_COUNT_2(++_COUNT_2);
				}
				if(msg.getText().startsWith(_OPTION_3))
				{
					votes.put(msg.getUser(), _OPTION_3);
					set_COUNT_3(++_COUNT_3);
				}
			}
		}
		
		
	}

	private static void processCardOption(int i) {
		int c = PixelManager.sideCrop;
		double r = PixelManager.ratio;
		int x = 200;
		int y = 300;
		
		// Select class
		RobotManager.click(PollOverlayScreen.bounds.x + x*i*r + c,
						   PollOverlayScreen.bounds.y + y*r + c);
		
		isPollOn = false;
		
		Robot robot = null;
		try {
			robot = new Robot();
			Thread.sleep(100);
			robot.mouseMove((int)(10),(int)(10));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void processClassOption(int i) {

		int c = PixelManager.sideCrop;
		double r = PixelManager.ratio;
		int x = 200;
		int y = 300;
		
		// Select class
		RobotManager.click(PollOverlayScreen.bounds.x + x*i*r + c,
						   PollOverlayScreen.bounds.y + y*r + c);

		x = 500;
		y = 600;
		
		// Click Choose Button
		RobotManager.click(PollOverlayScreen.bounds.x + x*r + c,
						   PollOverlayScreen.bounds.y + y*r + c);
		
		isPollOn = false;

	}



	private static int findMax() {
		
		int max = 0;
		int option = 0;
		if(_COUNT_1 > max) {max = _COUNT_1; option = 1;}
		if(_COUNT_2 > max) {max = _COUNT_2; option = 2;}
		//else if(_COUNT_2 == max) return 0;
		if(_COUNT_3 > max) {max = _COUNT_3; option = 3;}
		//else if(_COUNT_3 == max) return 0;
		
		pollMaxResult  = max;
		return option;
	}

	static void write(String text)
	{
		try {
			writer.write("PRIVMSG " + ChatParser.channel + " :" + text +"\r\n");
			writer.flush( );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void reset() {
		votes.clear();
		isPollOn = true;
		set_COUNT_1(0);
		set_COUNT_2(0);
		set_COUNT_3(0);
	}

	// Builds irc connection and sets read/write streams
	private static void setupEnvironment() {
		irc = new IRCConnection(user,pass);
		try {
			irc.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader = irc.getReader();
		writer = irc.getWriter();
	}

	public static int get_COUNT_1() {
		return _COUNT_1;
	}

	public static void set_COUNT_1(int _COUNT_1) {
		PlayManager._COUNT_1 = _COUNT_1;
		PollOverlayScreen.lblCount1.setText(_COUNT_1 + "");
		PollOverlayScreen.contentPane.repaint();
	}

	public static int get_COUNT_2() {
		return _COUNT_2;
	}

	public static void set_COUNT_2(int _COUNT_2) {
		PlayManager._COUNT_2 = _COUNT_2;
		PollOverlayScreen.lblCount2.setText(_COUNT_2 + "");
	}

	public static int get_COUNT_3() {
		return _COUNT_3;
	}

	public static void set_COUNT_3(int _COUNT_3) {
		PollOverlayScreen.lblCount3.setText(_COUNT_3 + "");
		PlayManager._COUNT_3 = _COUNT_3;
	}

}
