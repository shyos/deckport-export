package twitch.play.GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TPAFrame extends JFrame {

	public static JPanel contentPane;
	private SettingsScreen card1 = new SettingsScreen();
	public PollManagerScreen card2 = new PollManagerScreen();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TPAFrame frame = new TPAFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TPAFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 480, 240);
		contentPane = new JPanel();
		contentPane.setLayout(new CardLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(card1, "HOME");
		contentPane.add(card2, "POLL");
	

		
	}

}
