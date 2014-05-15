package extracter.upload;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import extracter.card.Deck;

public class Upload {
	
	public static String publishDeck(Deck deck)
	{
		HttpEntity entity = null;
        try {
               HttpClient httpclient = new DefaultHttpClient();
               HttpPost httppost = new HttpPost("http://localhost:8080/DeckLoadWeb/upload");
               List<NameValuePair> params = new ArrayList<NameValuePair>(2);
               Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
               String deckJson = gson.toJson(deck);
               params.add(new BasicNameValuePair("deck", deckJson));
               httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
               HttpResponse response = httpclient.execute(httppost);
               entity = response.getEntity();   
        } catch (Exception e) {
               e.printStackTrace();
        }
        String line="";
        if (entity != null) {
               InputStream instream = null;
              
            try {
               instream = entity.getContent();
               BufferedReader reader = new BufferedReader(new InputStreamReader(instream,"UTF-8"));
              line = reader.readLine();
             System.out.println(line);
              if(instream != null)
                     instream.close();
            }catch(Exception e){
               System.err.println(line+"\r\n");
            }
        }
		return line;
	}
}
