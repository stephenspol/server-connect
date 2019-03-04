import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
	
	/* Protocol states Status | Login | Play
	 *
	 */
	public static void main(String[] args) throws IOException {
		String address;
		int port;

		String username, password;

		Scanner sc = new Scanner(System.in);
		Console console = System.console();

		/*System.out.print("Server Address: ");
		address = sc.nextLine();
		System.out.print("Port: ");
		port = sc.nextInt();*/

		System.out.print("Username: ");
		username = sc.next();
		
		sc.nextLine();
		
		/*
		System.out.print("Password: ");
		password = sc.nextLine();*/
		//sc.close();
		
		if (console == null)
		{
			System.out.println("Couldn't get Console instance");
		}
		
		// Uses a array to store password to ensure security
		char passArray[] = console.readPassword("Password: ");
		Authentication server = new Authentication(username, passArray);
		
		//char accessToken[] = console.readPassword("Access Token: ");
		//char clientToken[] = console.readPassword("Client Token: ");
		
		//System.out.println(server.validate(accessToken));
		
		System.out.println(server.authenticate());
		/*
		InetSocketAddress host = new InetSocketAddress(address, port);
		Socket socket = new Socket();
		System.out.println("Connecting...");
		socket.connect(host, 3000);
		System.out.println("Connection Completed!");

		System.out.println("Creating streams...");
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		DataInputStream input = new DataInputStream(socket.getInputStream());
		System.out.println("Streams created");

		//beginning of Status Ping
		System.out.println("Attempting handshake..." + host.getAddress());
		byte [] handshakeMessage = createHandshakeMessage(address, port, 1);

		// C->S : Handshake State=1
		writeVarInt(output, handshakeMessage.length);
		output.write(handshakeMessage);
		System.out.println("Handshake sent");

		// C->S : Request
		output.writeByte(0x01); //size is one
		output.writeByte(0x00); //packet id for ping
		System.out.println("Status request sent");

		// S->C : Response
	    int size = readVarInt(input);
	    int packetId = readVarInt(input);

	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    if (packetId != 0x00) { //we want a status response
	        throw new IOException("Invalid packetID");
	    }
	    int length = readVarInt(input); //length of json string

	    if (length == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    if (length == 0) {
	        throw new IOException("Invalid string length.");
	    }

	    byte[] in = new byte[length];
	    input.readFully(in);  //read json string
	    String json = new String(in);


	    // C->S : Ping
	    long now = System.currentTimeMillis();
	    output.writeByte(0x09); //size of packet
	    output.writeByte(0x01); //0x01 for ping
	    output.writeLong(now); //time!?
	    System.out.println("Ping");

	    // S->C : Pong
	    readVarInt(input);
	    packetId = readVarInt(input);
	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    if (packetId != 0x01) {
	        throw new IOException("Invalid packetID");
	    }
	    long pingtime = input.readLong(); //read response

	    System.out.println("Pong " + pingtime + "");
	    // print out server info
	    System.out.println(json);

	    System.out.println("Status Ping Completeted! \n");
	    socket.close();


	    System.out.println("Starting Login");
		socket = new Socket();
		System.out.println("Connecting...");
		socket.connect(host, 3000);
		System.out.println("Connection Completed!");

		System.out.println("Creating streams...");
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		System.out.println("Streams created");

		//start of login
	    System.out.println("Attempting handshake..." + host.getAddress());
		handshakeMessage = createHandshakeMessage(address, port, 2);

		// C->S : Handshake State=2
		writeVarInt(output, handshakeMessage.length);
		output.write(handshakeMessage);
		System.out.println("Handshake sent");
		*/
		// C->S : Login Start
		/*byte [] bytes = username.getBytes(StandardCharsets.UTF_8);
	    writeVarInt(output, bytes.length + 1); //packet size
		output.write(0x00); //packet id
		output.write(bytes);*/
		/*ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    DataOutputStream string = new DataOutputStream(buffer);
	    string.writeByte(0x00); //packet id
	    writeString(string, username, StandardCharsets.UTF_8);

	    byte[] userInfo = buffer.toByteArray();

		writeVarInt(output, userInfo.length);
		output.write(userInfo);

		if (address != "127.0.0.1")
		{
			// S->C : Encryption Request

			// C->S : Encryption Response
		}

		// S->C : Login Success
		size = readVarInt(input);
	    packetId = readVarInt(input);

	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    length = readVarInt(input); //length of json string

	    if (length == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    if (length == 0) {
	        throw new IOException("Invalid string length.");
	    }

	    in = new byte[length];
	    input.readFully(in);  //read json string
	    json = new String(in);

	    // print out server info
	    System.out.println(json);*/
	}

	public static byte [] createHandshakeMessage(String host, int port, int state) throws IOException {
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    DataOutputStream handshake = new DataOutputStream(buffer);
	    handshake.writeByte(0x00); //packet id for handshake
	    writeVarInt(handshake, (state == 1)? 4 : 404); //status version = 4, minecraft version = 404(1.13.2)
	    writeString(handshake, host, StandardCharsets.UTF_8);
	    handshake.writeShort(port); //port
	    writeVarInt(handshake, state); //state (1 for status, 2 for login)

	    return buffer.toByteArray();
	}

	public static void writeString(DataOutputStream out, String string, Charset charset) throws IOException {
	    byte [] bytes = string.getBytes(charset);
	    writeVarInt(out, bytes.length);
	    out.write(bytes);
	}

	public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
	    while (true) {
	        if ((paramInt & 0xFFFFFF80) == 0) {
	          out.writeByte(paramInt);
	          return;
	        }

	        out.writeByte(paramInt & 0x7F | 0x80);
	        paramInt >>>= 7;
	    }
	}

	public static int readVarInt(DataInputStream in) throws IOException {
	    int i = 0;
	    int j = 0;
	    while (true) {
	        int k = in.readByte();
	        i |= (k & 0x7F) << j++ * 7;
	        if (j > 5) throw new RuntimeException("VarInt too big");
	        if ((k & 0x80) != 128) break;
	    }
	    return i;
	}
}
