import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Authentication {
	String username, password;
	
	String type, method;
	URL sessionServer;
	
	public Authentication(String user, String pswd) throws MalformedURLException
	{
		username = user;
		password = pswd;
		
		sessionServer = new URL("https://sessionserver.mojang.com/session/minecraft/join");
		
		type = "application/json";
		method = "POST";
	}
	
	
	
	public String authenticate() throws IOException
	{
		String payload = "{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
		System.out.println(payload);
		
		URL authServer = new URL("https://sneaky-player.glitch.me/authenticate");//"https://authserver.mojang.com/authenticate");
		
		HttpURLConnection conn = (HttpURLConnection) authServer.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", type );
		conn.setRequestProperty("Content-Length", String.valueOf(payload.length()));
		conn.setUseCaches(false);

		// Send to server
		OutputStream os = conn.getOutputStream();
		os.write(payload.getBytes());
		
		// Read server output
		BufferedReader in = new BufferedReader(
             new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response);
		
		return conn.getResponseMessage();
	}
}
