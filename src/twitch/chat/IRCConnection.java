package twitch.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class IRCConnection {

	// Server Info
	private String SERVER = "irc.twitch.tv";
	private int PORT = 6667;
	
	// Connection Info
    private String nick = "shyug";
    private String user = "shyug";
    private String oAuth = "oauth:ago75v1lrj9kcvswiy4bbn9l31zv9mc";
    

	private BufferedWriter writer;
	private BufferedReader reader;
	public IRCConnection(String user)
	{
		this.nick = user;
		this.user = user;
		ChatParser.channel = "#"+user;
	}
	public IRCConnection(String user, String oAuth)
	{
		this(user);
		this.oAuth = oAuth;
	}
	public void connect() throws IOException 
	{
		Socket socket;
		try {
			socket = new Socket(SERVER, PORT);
		    writer = new BufferedWriter(
		                new OutputStreamWriter(socket.getOutputStream( )));
		    reader = new BufferedReader(
		                new InputStreamReader(socket.getInputStream( )));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        try {
        	 writer.write("PASS " + oAuth + "\r\n");
        	 writer.write("NICK " + nick + "\r\n");
        	 writer.write("USER " + user + " 8 * : Java IRC Hacks Bot\r\n");
        	 writer.flush( );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
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

        writer.write("JOIN " + ChatParser.channel + "\r\n");
        writer.flush( );
	}
	public BufferedWriter getWriter() {
		return writer;
	}
	public void setWriter(BufferedWriter writer) {
		this.writer = writer;
	}
	public BufferedReader getReader() {
		return reader;
	}
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

}
