import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Authentication {
	// Username must be a email address if account is migrated
	private String username;
	private char[] password;
	
	private Scanner scanner;
	private File file;
	private String path;
	
	private String type, method;
	private URL sessionServer;
	
	public Authentication(String user, char[] pswd) throws IOException
	{
		username = user.toLowerCase();
		password = pswd;
		
		sessionServer = new URL("https://sessionserver.mojang.com/session/minecraft/join");
		
		type = "application/json";
		method = "POST";
		
		path = "Tokens.txt";
		
		file = new File(path);
		
		file.createNewFile();
		
		scanner = new Scanner(file);
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
		
		int startIndex = response.indexOf("accessToken") + 14; //14 is the length of accessToken:"
		int endIndex = startIndex + 32; // 32 is the length of the token
		
		String newAccessToken = response.substring(startIndex, endIndex);
		
		startIndex = response.indexOf("clientToken") + 14; //14 is the length of clientToken:"
		endIndex = startIndex + 32; // 32 is the length of the token
		
		String newClientToken = response.substring(startIndex, endIndex);
		
		writeTokensToFile(newAccessToken, newClientToken);
		
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
	
	private void writeTokensToFile(String accessToken, String clientToken)
	{
		int lineNum = 0;
		boolean foundLine = false;
		String line = "";
		
		while (!foundLine && scanner.hasNextLine()) {
	        line = scanner.nextLine();
	        lineNum++;
	        if(line.matches("(.*)" + username + "(.*)")) { 
	            foundLine = true;
	        }
	    }
		
		if (foundLine)
		{
			System.out.println("Here is the line: " + line + "(line: " + lineNum + ")");
		}
		
		else
		{
			try {
				FileOutputStream out = new FileOutputStream(path);
				line = "Username: " + username + "\r\nAccess Token: " + accessToken + "\r\nClient Token: " + clientToken + "\r\n\r\n";
				
				out.write(line.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private String[] readTokensFromFile()
	{
		String[] tokens = {"", ""};
		
		int lineNum = 0;
		boolean foundLine = false;
		String line = "";
		
		while (!foundLine && scanner.hasNextLine()) {
	        line = scanner.nextLine();
	        lineNum++;
	        if(line.matches("(.*)" + username + "(.*)")) { 
	            foundLine = true;
	            
	            line = scanner.nextLine();
	            tokens[0] = line.substring(20);
	            
	            line = scanner.nextLine();
	            tokens[1] = line.substring(20);
	        }
	    }
		
		return tokens;
	}
	
	public String[] getTokens()
	{
		return readTokensFromFile();
	}
	
	public String getUsername()
	{
		return username;
	}
}