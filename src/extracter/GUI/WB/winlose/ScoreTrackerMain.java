package extracter.GUI.WB.winlose;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ScoreTrackerMain {
	public static ScoreTrackerFrame frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final KeyHookThread khT = new KeyHookThread();
					khT.start();
					frame = new ScoreTrackerFrame();
					frame.setTitle("Score Tracker");
					frame.addWindowListener(new WindowAdapter(){
		                public void windowClosing(WindowEvent e){
		                	khT.endJob();
		                }
		            });
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
  
	}
}
