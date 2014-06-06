package twitch.play;

import java.io.BufferedWriter;
import java.io.IOException;

import twitch.chat.ChatParser;
import twitch.chat.IRCConnection;

public class CommandThread extends Thread {

	static BufferedWriter writer;
	public void run() {
		try {
			IRCConnection irc = new IRCConnection(PlayManager.user, PlayManager.pass);
			try {
				irc.connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread.sleep(PlayManager.twitchDelay * 1000);
			writer = irc.getWriter();
			classSelection();
			while(PlayManager.currentCardIndex <= 30)
			{
				Thread.sleep(PlayManager.twitchDelay * 1000);
				cardSelection();
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
		write(PlayManager.startCardPoll
				+ PlayManager.currentCardIndex);
		Thread.sleep(PlayManager.cardTimer*1000);
		write(PlayManager.endCardPoll
				+ PlayManager.currentCardIndex);
	}

	private void classSelection() throws IOException, InterruptedException {
		write(PlayManager.startClassPoll);
		Thread.sleep(PlayManager.classTimer * 1000);
		write(PlayManager.endClassPoll);
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
