package twitch;

import java.awt.EventQueue;

import extracter.GUI.WB.TrainingApp;
import twitch.play.GUI.PollOverlayScreen;

public class PollOverlayMain {
	public static PollOverlayScreen frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new PollOverlayScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					TrainingApp.showMessageDialog(null, "Hearthstone.exe screen not found.");
				}
			}
		});
	}
}

