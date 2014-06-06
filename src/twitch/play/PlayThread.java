package twitch.play;

import java.io.IOException;

public class PlayThread extends Thread {

	public void run(){
		try {
			PlayManager.main(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
