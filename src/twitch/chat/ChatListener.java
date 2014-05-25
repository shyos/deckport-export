package twitch.chat;

import java.io.*;
import java.net.*;

public class ChatListener {

	/*IRCConnection irc;
	public ChatListener(String user, String pass)
	{
		irc = new IRCConnection(user,pass);
		try {
			irc.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen(){
		BufferedReader reader = irc.getReader();
	}*/
	
    public static void main(String[] args) throws Exception {

        // The server to connect to and our details.
        String server = "irc.twitch.tv";
        String nick = "shyug";
        String login = "shyug";

        // The channel which the bot will join.
        String channel = "#massansc";
        
        // Connect directly to the IRC server.
        Socket socket = new Socket(server, 6667);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));
        
        // Log on to the server.
        writer.write("PASS " + "oauth:ago75v1lrj9kcvswiy4bbn9l31zv9mc" + "\r\n");
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
        writer.flush( );
        
        // Read lines from the server until it tells us we have connected.
        String line = null;
        while ((line = reader.readLine( )) != null) {
        	System.out.println(line);
            if (line.indexOf("004") >= 0) {
                // We are now logged in.
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nickname is already in use.");
                return;
            }
        }
        
        // Join the channel.
        writer.write("JOIN " + channel + "\r\n");
        writer.flush( );
        
        // Keep reading lines from the server.
        while ((line = reader.readLine( )) != null) {
            if (line.toLowerCase( ).startsWith("PING ")) {
                // We must respond to PINGs to avoid being disconnected.
                writer.write("PONG " + line.substring(5) + "\r\n");
                writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
                writer.flush( );
            }
            else {
                ChatMessage msg = ChatParser.parse(line);
                System.out.println(line);
                if(msg!=null)
                	System.out.println(msg.toString());
            }
        }
    }

}