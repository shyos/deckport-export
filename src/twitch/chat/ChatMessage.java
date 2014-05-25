package twitch.chat;

public class ChatMessage {
	private String user;
	private String text;
	
	public ChatMessage(String user,String text)
	{
		this.setUser(user);
		this.setText(text);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String toString()
	{
		return user + " : " + text;
	}
}
