package twitch.play;

import java.io.BufferedWriter;
import java.io.IOException;

import twitch.chat.ChatParser;
import twitch.chat.IRCConnection;

public class CommandThread extends Thread {

	static BufferedWriter writer;
	public void run() {
		try {
			IRCConnection irc = new IRCConnection(StartDecker.user, StartDecker.pass);
			try {
				irc.connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writer = irc.getWriter();
			classSelection();
			Thread.sleep(StartDecker.twitchDelay * 1000);
			while(StartDecker.currentCardIndex <= 30)
			{
				cardSelection();
				Thread.sleep(StartDecker.twitchDelay * 1000);
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
		write(StartDecker.startCardPoll
				+ StartDecker.currentCardIndex);
		Thread.sleep(StartDecker.cardTimer*1000);
		write(StartDecker.endCardPoll
				+ StartDecker.currentCardIndex);
	}

	private void classSelection() throws IOException, InterruptedException {
		write(StartDecker.startClassPoll);
		Thread.sleep(StartDecker.classTimer * 1000);
		write(StartDecker.endClassPoll);
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
