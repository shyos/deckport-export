package twitch.play;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import twitch.chat.ChatMessage;
import twitch.chat.ChatParser;
import twitch.chat.IRCConnection;
import twitch.play.GUI.PollOverlay;

public class StartDecker {
    // Connection infos
	static String user = "shyug";
    static String pass = "oauth:ago75v1lrj9kcvswiy4bbn9l31zv9mc";
    
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
	static String startClassPoll = "!startarenaclasspick";
	static String endClassPoll = "!endarenaclasspick";
	static String startCardPoll = "!startcardpick";
	static String endCardPoll= "!endcardpick";
	
	// Bot Options
	static int currentCardIndex = 1;
	static boolean allowMultipleVotes = false;
	boolean allowVoteChanges = false;
	
	// Bot Timer Options (in seconds)
	static int classTimer = 20;
	static int cardTimer = 10;
	static int twitchDelay = 5;
	
	static Map<String,String> votes = new HashMap<String,String>();
	private static int pollMaxResult = 0;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		setupEnvironment();
		PollOverlay.main(null);
		new CommandThread().start();
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
				processClassOption();
			}
			if(msg.getText().equalsIgnoreCase(startCardPoll + currentCardIndex))
			{
				reset();
			}
			if(msg.getText().equalsIgnoreCase(endCardPoll + currentCardIndex))
			{
				System.out.println("Card Order:" + currentCardIndex + "Option:" + findMax() + " with " + pollMaxResult + " votes out of " + (_COUNT_1+_COUNT_2+_COUNT_3));
				processCardOption();
				currentCardIndex++;
			}
		}

		if(allowMultipleVotes)
		{
			
		}
		else
		{
			// If user didnt cast his vote yet, let him vote
			//if(!votes.keySet().contains(msg.getUser()))
			{
				if(msg.getText().equalsIgnoreCase(_OPTION_1))
				{
					votes.put(msg.getUser(), _OPTION_1);
					set_COUNT_1(++_COUNT_1);
				}
				if(msg.getText().equalsIgnoreCase(_OPTION_2))
				{
					votes.put(msg.getUser(), _OPTION_2);
					set_COUNT_2(++_COUNT_2);
				}
				if(msg.getText().equalsIgnoreCase(_OPTION_3))
				{
					votes.put(msg.getUser(), _OPTION_3);
					set_COUNT_3(++_COUNT_3);
				}
			}
		}
		
		
	}

	private static void processCardOption() {
		// TODO Auto-generated method stub
		
	}

	private static void processClassOption() {
		// TODO Auto-generated method stub
		
	}

	private static String findMax() {
		
		int max = 0;
		String option = null;
		if(_COUNT_1 > max) {max = _COUNT_1; option = _OPTION_1;}
		if(_COUNT_2 > max) {max = _COUNT_2; option = _OPTION_2;}
		else if(_COUNT_2 == max) return null;
		if(_COUNT_3 > max) {max = _COUNT_3; option = _OPTION_3;}
		else if(_COUNT_3 == max) return null;
		
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
		StartDecker._COUNT_1 = _COUNT_1;
		PollOverlay.lblCount1.setText(_COUNT_1 + "");
		PollOverlay.contentPane.repaint();
	}

	public static int get_COUNT_2() {
		return _COUNT_2;
	}

	public static void set_COUNT_2(int _COUNT_2) {
		StartDecker._COUNT_2 = _COUNT_2;
		PollOverlay.lblCount2.setText(_COUNT_2 + "");
	}

	public static int get_COUNT_3() {
		return _COUNT_3;
	}

	public static void set_COUNT_3(int _COUNT_3) {
		PollOverlay.lblCount3.setText(_COUNT_3 + "");
		StartDecker._COUNT_3 = _COUNT_3;
	}

}
