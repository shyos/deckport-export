package twitch.chat;

public class ChatParser {
	public static String channel = "#shyug";
	public static ChatMessage parse(String line)
	{
		if(line.contains("PRIVMSG "+channel))
		{
			return new ChatMessage(findUser(line),findMessage(line));
		}
		return null;
	}

	private static String findUser(String line) {
		return line.substring(1,line.indexOf('!'));
	}

	private static String findMessage(String line) {
		return line.substring(line.indexOf(channel+" :")+channel.length()+2);
	}
}
