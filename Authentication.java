import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Authentication {
	// Username must be a email address if account is migrated
	String username;
	char[] password;
	
	String type, method;
	URL sessionServer;
	
	public Authentication(String user, char[] pswd) throws MalformedURLException
	{
		username = user;
		password = pswd;
		
		sessionServer = new URL("https://sessionserver.mojang.com/session/minecraft/join");
		
		type = "application/json";
		method = "POST";
	}
	
	
	
	public String authenticate() throws IOException
	{
		String payload = "{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\"" + username + "\",\"password\":\"" + new String(password) + "\"}";
		//System.out.println(payload);
		
		URL authServer = new URL("https://authserver.mojang.com/authenticate");
		
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
	
	public String refresh(char[] accessToken) throws IOException
	{
		String payload = "{\"accessToken\":\"" + new String(accessToken) + "\"}";
		//System.out.println(payload);
		
		URL authServer = new URL("https://authserver.mojang.com/refresh");
		
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
	
	public String validate(char[] accessToken) throws IOException
	{
		String payload = "{\"accessToken\":\"" + new String(accessToken) + "\"}";
		//System.out.println(payload);
		
		URL authServer = new URL("https://authserver.mojang.com/validate");
		
		HttpURLConnection conn = (HttpURLConnection) authServer.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", type );
		conn.setRequestProperty("Content-Length", String.valueOf(payload.length()));
		conn.setUseCaches(false);

		// Send to server
		OutputStream os = conn.getOutputStream();
		os.write(payload.getBytes());
		
		return conn.getResponseMessage();
	}
	
	public String signout() throws IOException
	{
		String payload = "{\"username\":\"" + username + "\",\"password\":\"" + new String(password) + "\"}";
		//System.out.println(payload);
		
		URL authServer = new URL("https://authserver.mojang.com/signout");
		
		HttpURLConnection conn = (HttpURLConnection) authServer.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", type );
		conn.setRequestProperty("Content-Length", String.valueOf(payload.length()));
		conn.setUseCaches(false);

		// Send to server
		OutputStream os = conn.getOutputStream();
		os.write(payload.getBytes());
		
		return conn.getResponseMessage();
	}
	
	public String invalidate(char[] accessToken, char[] clientToken) throws IOException
	{
		String payload = "{\"accessToken\":\"" + new String(accessToken) + "\",\"clientToken\":\"" + new String(clientToken) + "\"}";
		//System.out.println(payload);
		
		URL authServer = new URL("https://authserver.mojang.com/invalidate");
		
		HttpURLConnection conn = (HttpURLConnection) authServer.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", type );
		conn.setRequestProperty("Content-Length", String.valueOf(payload.length()));
		conn.setUseCaches(false);

		// Send to server
		OutputStream os = conn.getOutputStream();
		os.write(payload.getBytes());
		
		return conn.getResponseMessage();
	}
}
