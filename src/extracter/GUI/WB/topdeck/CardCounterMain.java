package extracter.GUI.WB.topdeck;

import java.awt.EventQueue;

public class CardCounterMain {
	public static CardCounterFrame frame;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 frame = new CardCounterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
