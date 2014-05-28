package twitch.play;

import java.io.BufferedWriter;
import java.io.IOException;

import twitch.chat.ChatManager;
import twitch.chat.ChatParser;
import twitch.chat.IRCConnection;

public class CommandThread extends Thread {

	static BufferedWriter writer;
	public void run() {
		try {
			IRCConnection irc = new IRCConnection(ChatManager.user, ChatManager.pass);
			try {
				irc.connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writer = irc.getWriter();
			classSelection();
			Thread.sleep(ChatManager.twitchDelay * 1000);
			while(ChatManager.currentCardIndex <= 30)
			{
				cardSelection();
				Thread.sleep(ChatManager.twitchDelay * 1000);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cardSelection() throws IOException, InterruptedException {
		write(ChatManager.startCardPoll
				+ ChatManager.currentCardIndex);
		Thread.sleep(ChatManager.cardTimer*1000);
		write(ChatManager.endCardPoll
				+ ChatManager.currentCardIndex);
	}

	private void classSelection() throws IOException, InterruptedException {
		write(ChatManager.startClassPoll);
		Thread.sleep(ChatManager.classTimer * 1000);
		write(ChatManager.endClassPoll);
	}
	static void write(String text)
	{
		try {
			writer.write("PRIVMSG " + ChatParser.channel + " :" + text + "\r\n");
			writer.flush( );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
