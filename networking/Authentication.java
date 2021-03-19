package networking;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Authentication {
	// Username must be a email address if account is migrated
	private String username;
	private char[] password;
	
	private Logger log;
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

		log.setUseParentHandlers(false);

		log.setLevel(Level.FINER);

		ConsoleHandler consoleHandler = new ConsoleHandler();

		log.addHandler(consoleHandler);

		consoleHandler.setLevel(Level.FINER);

		username = user.toLowerCase().trim(); // Make username standerized
		password = pswd;
		
		sessionServer = new URL("https://sessionserver.mojang.com/session/minecraft/join");
		
		type = "application/json";
		method = "POST";
		
		path = "../Tokens.txt";
		
		file = new File(path);
		
		boolean fileCreated = file.createNewFile();

		log.log(Level.FINE, "Created new file at {0}: {1}", new Object[] {path, fileCreated});

		// Need to create Access Token
		if (fileCreated)
		{
			authenticate();
		}

		else
		{
			String[] data = readFromFile();

			if (data[1].equals(""))
			{
				authenticate();

				data = readFromFile();

				validate(data[1]);
			}

			if (!validate(data[1]))
			{
				refresh(data[1], data[2]);
			}
		}
	}
	
	public boolean authenticate() throws IOException
	{
		String payload = "{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\"" + username + "\",\"password\":\"" + new String(password) + "\"}";
		log.finest(payload);

		return serverConnect("authenticate", payload);
	}
	
	public boolean refresh(String accessToken, String clientToken) throws IOException
	{
		String payload = "{\"accessToken\":\"" + accessToken + "\",\"clientToken\":\"" + clientToken + "\"}";
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
	
	private boolean writeToFile(String name, String accessToken, String clientToken, String UUID)
	{
		int lineNum = 0;
		boolean foundLine = false;
		String line = "";

		try(Scanner scanner = new Scanner(file)) {
			
			while (!foundLine && scanner.hasNextLine()) {
				line = scanner.nextLine();
				lineNum++;
				if(line.matches("(.*)" + username + "(.*)")) { 
					foundLine = true;
				}
			}
		} catch (FileNotFoundException e) {
			log.log(Level.WARNING, "File not located at: {0}", file.getPath());
		}
		
		// Close stream once done using try with resource
		try (FileOutputStream out = new FileOutputStream(path)) {
			
			line = "Username: " + username + "\r\nName: " + name + "\r\nAccess Token: " + accessToken + "\r\nClient Token: " + clientToken + "\r\nUUID: " + UUID + "\r\n\r\n";
			
			out.write(line.getBytes());

			log.log(Level.FINER, "New line: {0} (line number: {1})\n", new Object[]{line, lineNum});

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String[] readFromFile()
	{
		String[] data = {"", "", "", ""};
		
		boolean foundLine = false;
		String line = "";

		try (Scanner scanner = new Scanner(file)) {
		
			while (!foundLine && scanner.hasNextLine()) {
				line = scanner.nextLine();
				if(line.matches("Username: " + username)) { 
					foundLine = true;

					// Read Name
					line = scanner.nextLine();
					data[0] = line.substring(6);
					
					// Read Access Token
					line = scanner.nextLine();
					data[1] = line.substring(14);
					
					// Read Client Token
					line = scanner.nextLine();
					data[2] = line.substring(14);

					// Read UUID
					line = scanner.nextLine();
					data[3] = line.substring(6);

					log.log(Level.FINER, "Name: [{0}]", data[0]);
					log.log(Level.FINER, "Access Token: [{0}]", data[1]);
					log.log(Level.FINER, "Client Token: [{0}]", data[2]);
					log.log(Level.FINER, "UUID: [{0}]\n", data[3]);
				}
			}
		} catch (FileNotFoundException e) {
			log.log(Level.WARNING, "File not located at: {0}", file.getPath());
		}
		
		if (!foundLine)
		{
			log.warning("Username not found\n");
		}

		return data;
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

		os.close();

		StringBuilder response = new StringBuilder();
		
		// Read server output
		try (BufferedReader in = new BufferedReader(
			new InputStreamReader(conn.getInputStream())))
		{	
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			log.log(Level.FINE, "{0}: {1}", new Object[] {s, conn.getResponseMessage()});

			log.log(Level.FINER, "Server Response: {0}\n", response);
			
			if (s.equals("authenticate") || s.equals("refresh")) 
			{
				int startIndex = response.indexOf("accessToken") + 14; //14 is the length of accessToken:"
				int endIndex = startIndex + 308; // 308 is the length of the token
				
				String newAccessToken = response.substring(startIndex, endIndex);
				
				startIndex = response.indexOf("clientToken") + 14; //14 is the length of clientToken:"
				endIndex = startIndex + 32; // 32 is the length of the token
				
				String newClientToken = response.substring(startIndex, endIndex);

				startIndex = response.indexOf("id") + 5;
				endIndex = startIndex + 32; // length of UUID

				String newUUID = response.substring(startIndex, endIndex);

				startIndex = response.indexOf("name") + 7;
				endIndex = response.indexOf("id") - 3; // id is the next field

				String newName = response.substring(startIndex, endIndex);
				
				writeToFile(newName, newAccessToken, newClientToken, newUUID);
			}
		} catch (IOException e) {
			log.log(Level.WARNING, "{0}: {1}", new Object[] {s, conn.getResponseMessage()});

			return false;
		}

		return true;
	}
	
	public String[] getTokens()
	{
		String[] data = readFromFile();

		return new String[]{data[1], data[2]};
	}

	public String getUUID()
	{
		String[] data = readFromFile();

		return data[3];
	}

	public String getName()
	{
		String[] data = readFromFile();

		return data[0];
	}
	
	public String getUsername()
	{
		return username;
	}
}