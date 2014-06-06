package twitch.play;

import twitch.play.GUI.SettingsScreen;

public class PlayOptions {
	public String user;
	public String oAuth;
	public String channel;
	public int classDelay;
	public int cardDelay;
	public int twitchDelay;
	public PlayOptions(String string, String string2, char[] cs, String string3, String string4, String string5) {
		this.user = string;
		this.channel = string2;
		this.oAuth = new String(cs);
		this.classDelay = Integer.parseInt(string3);
		this.cardDelay = Integer.parseInt(string4);
		this.twitchDelay = Integer.parseInt(string5);
	}
	public void setAll() {
		PlayConstants.user = user;
		PlayConstants.channel = channel;
		PlayConstants.oAuth = oAuth;
		PlayConstants.classDelay = classDelay;
		PlayConstants.cardDelay = cardDelay;
		PlayConstants.twitchDelay = twitchDelay;
		
		SettingsScreen.textField.setText(user);
		SettingsScreen.textField_1.setText(channel);
		SettingsScreen.textField_2.setText(oAuth);
		SettingsScreen.textField_3.setText(classDelay+"");
		SettingsScreen.textField_4.setText(cardDelay+"");
		SettingsScreen.textField_5.setText(twitchDelay+"");
	}
}
