import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Authentication {
	// Username must be a email address if account is migrated
	private String username;
	private char[] password;
	
	private Logger log;
	private Scanner scanner;
	private File file;
	private String path;
	
	private String type;
	private String method;
	private URL sessionServer;

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_LENGTH = "Content-Length";
	
	public Authentication(String user, char[] pswd) throws IOException
	{
		log = Logger.getLogger(Authentication.class.getName());

		username = user.toLowerCase();
		password = pswd;
		
		sessionServer = new URL("https://sessionserver.mojang.com/session/minecraft/join");
		
		type = "application/json";
		method = "POST";
		
		path = "Tokens.txt";
		
		file = new File(path);
		
		boolean fileCreated = file.createNewFile();

		log.log(Level.FINE, "Created new file at {}: {}", new Object[] {path, fileCreated});

		scanner = new Scanner(file);

		// Need to create Access Token
		if (fileCreated)
		{
			authenticate();

			String[] tokens = readTokensFromFile();

			validate(tokens[0]);
		}

		else
		{
			String[] tokens = readTokensFromFile();

			if(!validate(tokens[0]))
			{
				refresh(tokens[0]);
			}
		}
	}
	
	public boolean authenticate() throws IOException
	{
		String payload = "{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\"" + username + "\",\"password\":\"" + new String(password) + "\"}";
		log.finest(payload);

		return serverConnect("authenticate", payload);
	}
	
	public boolean refresh(String accessToken) throws IOException
	{
		String payload = "{\"accessToken\":\"" + accessToken + "\"}";
		log.finest(payload);
		
		return serverConnect("refresh", payload);
	}
	
	public boolean validate(String accessToken) throws IOException
	{
		String payload = "{\"accessToken\":\"" + accessToken + "\"}";
		log.finest(payload);
		
		return serverConnect("validate", payload);
	}
	
	public boolean signout() throws IOException
	{
		String payload = "{\"username\":\"" + username + "\",\"password\":\"" + new String(password) + "\"}";
		log.finest(payload);
		
		return serverConnect("signout", payload);
	}
	
	public boolean invalidate(String accessToken, String clientToken) throws IOException
	{
		String payload = "{\"accessToken\":\"" + accessToken + "\",\"clientToken\":\"" + clientToken + "\"}"; 
		log.finest(payload);

		return serverConnect("invalidate", payload);
	}
	
	private boolean writeTokensToFile(String accessToken, String clientToken)
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
		
		// Close stream once done using try with resource
		try (FileOutputStream out = new FileOutputStream(path)) {
			
			line = "Username: " + username + "\r\nAccess Token: " + accessToken + "\r\nClient Token: " + clientToken + "\r\n\r\n";
			
			out.write(line.getBytes());

			log.log(Level.FINER, "New line: {0} (line number: {1})", new Object[]{line, lineNum});

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String[] readTokensFromFile()
	{
		String[] tokens = {"", ""};
		
		boolean foundLine = false;
		String line = "";
		
		while (!foundLine && scanner.hasNextLine()) {
	        line = scanner.nextLine();
	        if(line.matches("(.*)" + username + "(.*)")) { 
	            foundLine = true;
	            
	            line = scanner.nextLine();
	            tokens[0] = line.substring(20);
	            
	            line = scanner.nextLine();
				tokens[1] = line.substring(20);
				
				log.log(Level.FINER, "Access Token: [{}]", tokens[0]);
				log.log(Level.FINER, "Client Token: [{}]", tokens[1]);
	        }
		}
		
		if (!foundLine)
		{
			log.warning("Tokens not found");
		}
		
		return tokens;
	}

	private boolean serverConnect(String s, String payload) throws IOException
	{
		URL authServer = new URL("https://authserver.mojang.com/" + s);
		
		HttpURLConnection conn = (HttpURLConnection) authServer.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty(CONTENT_TYPE, type );
		conn.setRequestProperty(CONTENT_LENGTH, String.valueOf(payload.length()));
		conn.setUseCaches(false);

		// Send to server
		OutputStream os = conn.getOutputStream();
		os.write(payload.getBytes());
		
		// Read server output
		BufferedReader in = new BufferedReader(
             new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		log.log(Level.FINE, "{}: {}", new Object[] {s, conn.getResponseMessage()});

		int status = conn.getResponseCode();

		if (status >= 200 && status < 300)
		{
			log.log(Level.FINER, "Server Response: {}", response);
			
			if (s.equals("authenticate")) 
			{
				int startIndex = response.indexOf("accessToken") + 14; //14 is the length of accessToken:"
				int endIndex = startIndex + 32; // 32 is the length of the token
				
				String newAccessToken = response.substring(startIndex, endIndex);
				
				startIndex = response.indexOf("clientToken") + 14; //14 is the length of clientToken:"
				endIndex = startIndex + 32; // 32 is the length of the token
				
				String newClientToken = response.substring(startIndex, endIndex);
				
				writeTokensToFile(newAccessToken, newClientToken);
			}

			return true;
		}

		else
		{
			log.log(Level.WARNING, "Server Response: {}", response);

			return false;
		}
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