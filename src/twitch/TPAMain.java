package twitch;

import java.awt.EventQueue;

import twitch.play.GUI.TPAFrame;

public class TPAMain {
	public static TPAFrame frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new TPAFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
