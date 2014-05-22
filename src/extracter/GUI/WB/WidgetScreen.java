package extracter.GUI.WB;

import javax.swing.JPanel;
import javax.swing.JButton;

import extracter.GUI.WB.topdeck.CardCounterMain;
import extracter.GUI.WB.winlose.ScoreTrackerMain;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WidgetScreen extends JPanel {

	/**
	 * Create the panel.
	 */
	public WidgetScreen() {
		setLayout(null);
		
		JButton btnDeckTracker = new JButton("Deck Tracker");
		btnDeckTracker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardCounterMain.main(null);
			}
		});
		btnDeckTracker.setBounds(102, 83, 200, 38);
		add(btnDeckTracker);
		
		JButton btnScoreTracker = new JButton("Score Tracker");
		btnScoreTracker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScoreTrackerMain.main(null);
			}
		});
		btnScoreTracker.setBounds(102, 148, 200, 38);
		add(btnScoreTracker);

	}

}
