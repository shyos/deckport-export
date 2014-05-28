package twitch.play;

import java.io.IOException;

import twitch.chat.ChatManager;

public class ChatThread extends Thread {

	public void run(){
		try {
			ChatManager.main(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
