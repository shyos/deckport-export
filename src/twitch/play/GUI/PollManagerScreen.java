package twitch.play.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import twitch.PollOverlayMain;
import twitch.play.CommandThread;

public class PollManagerScreen extends JPanel {
	public static JTextArea txtrTextArea;
	public static JScrollPane sp;
	/**
	 * Create the panel.
	 */
	public PollManagerScreen() {
		setLayout(null);
		
		JButton btnStartPoll = new JButton("Start Poll");
		btnStartPoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start GUI
				PollOverlayMain.main(null);
				
				// Start Commander Thread which sends commands to twitch chat.
				new CommandThread().start();
			}
		});
		
		btnStartPoll.setBounds(10, 158, 89, 23);
		add(btnStartPoll);
		
 
		
		txtrTextArea = new JTextArea();
		txtrTextArea.setText("Connected");
		txtrTextArea.setBounds(10, 33, 430, 160);
		sp = new JScrollPane(txtrTextArea); 
		sp.setBounds(10, 33, 430, 114);
		add(sp);
		
		DefaultCaret caret = (DefaultCaret) txtrTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JLabel lblTwitchChat = new JLabel("Twitch Chat");
		lblTwitchChat.setBounds(10, 8, 89, 14);
		add(lblTwitchChat);

	}
}
