package twitch.play.GUI;

import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import twitch.play.PlayConstants;
import twitch.play.PlayOptions;
import twitch.play.PlayThread;
import util.FileManager;
import extracter.Constants;
import extracter.GUI.WB.TrainingApp;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class SettingsScreen extends JPanel {
	public static JTextField textField;
	public static JTextField textField_1;
	public static JPasswordField  textField_2;
	public static JTextField textField_3;
	public static JTextField textField_4;
	public static JTextField textField_5;

	/**
	 * Create the panel.
	 */
	public SettingsScreen() {
		setLayout(null);

		setBounds(100, 100, 480, 240);
		JLabel lblDeckloadWindowsApp = new JLabel("Twitch Plays Arena");
		lblDeckloadWindowsApp.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		lblDeckloadWindowsApp.setBounds(167, 21, 123, 30);
		add(lblDeckloadWindowsApp);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(47, 65, 77, 14);
		add(lblUsername);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				textField_1.setText("#"+textField.getText());
			}
		});
		textField.setBounds(134, 62, 86, 20);
		add(textField);
		
		JLabel lblOauthKey = new JLabel("oAuth Key:");
		lblOauthKey.setBounds(47, 127, 77, 14);
		add(lblOauthKey);
		
		JLabel lblChannel = new JLabel("Channel:");
		lblChannel.setBounds(47, 96, 77, 14);
		add(lblChannel);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(134, 93, 86, 20);
		add(textField_1);
		
		textField_2 = new JPasswordField();
		textField_2.setBounds(134, 124, 86, 20);
		add(textField_2);
		
		JCheckBox chckbxRemember = new JCheckBox("Remember Me");
		chckbxRemember.setSelected(true);
		chckbxRemember.setBounds(134, 163, 108, 23);
		add(chckbxRemember);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(textField.getText().isEmpty())
				{
					TrainingApp.showMessageDialog(null, "Username cannot be empty. Should be twitch username");
				}
				else if(textField_2.getPassword().length == 0)
				{
					TrainingApp.showMessageDialog(null, "oAuth code cannot be empty. Acquire it from http://twitchapps.com/tmi/");
				}
				else if(textField_3.getText().isEmpty())
				{
					TrainingApp.showMessageDialog(null, "Class Timer shouldnt be empty.");
				}
				else if(textField_4.getText().isEmpty())
				{
					TrainingApp.showMessageDialog(null, "Card Timer shouldnt be empty.");
				}
				else if(textField_5.getText().isEmpty())
				{
					TrainingApp.showMessageDialog(null, "Stream Delay shouldnt be empty.");
				}
				else{
				    PlayOptions po = new PlayOptions(textField.getText(),
				    					 textField_1.getText(),
				    					 textField_2.getPassword(),
				    					 textField_3.getText(),
				    					 textField_4.getText(),
				    					 textField_5.getText());
				    FileManager.writeToFile(po, Constants.TPAFilename);
				    new PlayThread().start();
				}
			}
		});
		btnNewButton.setBounds(338, 163, 89, 23);
		add(btnNewButton);
		
		JLabel label = new JLabel("");
		label.setBounds(109, 163, 46, 14);
		add(label);
		
		JLabel lblNewLabel = new JLabel("Class Timer:");
		lblNewLabel.setBounds(248, 65, 83, 14);
		add(lblNewLabel);
		
		textField_3 = new JTextField("60");
		textField_3.setBounds(341, 62, 86, 20);
		add(textField_3);
		
		JLabel lblCardTimer = new JLabel("Card Timer:");
		lblCardTimer.setBounds(248, 96, 83, 14);
		add(lblCardTimer);
		
		textField_4 = new JTextField("60");
		textField_4.setBounds(341, 93, 86, 20);
		add(textField_4);
		
		JLabel lblStreamDelay = new JLabel("Stream Delay:");
		lblStreamDelay.setBounds(248, 127, 83, 14);
		add(lblStreamDelay);
		
		textField_5 = new JTextField("30");
		textField_5.setBounds(341, 124, 86, 20);
		add(textField_5);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(230, 62, 8, 82);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(47, 154, 375, 2);
		add(separator_1);
		
		JButton btnTest = new JButton("Test");
		btnTest.setBounds(248, 163, 89, 23);
		btnTest.setEnabled(false);
		add(btnTest);
	}
}
