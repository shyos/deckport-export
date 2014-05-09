package extracter.GUI;

import java.awt.EventQueue;

public class CrawlApp {

	public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						//menu screen is displayed in a thread other than main thread
						GUIManager window = new GUIManager();
						window.getFrame().setVisible(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
