import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Main {
	
	// Protocol states Status | Login | Play
	public static void main(String[] args) throws IOException {
		String address;
		int port;
		
		int data;

		String username, password;
		
		boolean compressionEnabled = false;
		int uncompressedSize = -1;

		Scanner sc = new Scanner(System.in);
		Console console = System.console();

		System.out.print("Server Address: ");
		address = sc.nextLine();
		System.out.print("Port: ");
		port = sc.nextInt();

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
		
		//System.out.println(server.authenticate());
		
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
		byte [] handshakeMessage = Packet.createHandshakeMessage(address, port, 1);

		// C->S : Handshake State=1
		Packet.writeVarInt(output, handshakeMessage.length);
		output.write(handshakeMessage);
		System.out.println("Handshake sent");

		// C->S : Request
		output.writeByte(0x01); //size is one
		output.writeByte(0x00); //packet id for ping
		System.out.println("Status request sent");

		// S->C : Response
		String json = (String) Packet.readPacket(input)[0];

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
		
		// C->S : Login Start
		/*byte [] bytes = username.getBytes(StandardCharsets.UTF_8);
	    writeVarInt(output, bytes.length + 1); //packet size
		output.write(0x00); //packet id
		output.write(bytes);*/
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

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
		
		// S->C : Set Compression (optional)
		/*size = readVarInt(input);
		packetId = readVarInt(input);

	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }
	    
	    if (size == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    if (size == 0) {
	        throw new IOException("Invalid string length.");
	    }
	    
	    data = readVarInt(input);
	    
	    System.out.println("Compression Size: " + data);
	    
	    // if received set compression packet and is not a negative number, enable compression
	    if (data >= 0) compressionEnabled = true;
		*/
		// S->C : Login Success
		size = readVarInt(input);
		if (compressionEnabled) uncompressedSize = readVarInt(input); // if 0, data is not compressed
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

	    System.out.println("UUID: " + json);
	    
	    
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
	    
	    System.out.println("Username: " + json);
	    
	    // S->C : Join Game
	    System.out.println("Joined Game!");
	    size = readVarInt(input);
	    if (compressionEnabled) uncompressedSize = readVarInt(input);
	    packetId = readVarInt(input);

	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    if (size == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    if (size == 0) {
	        throw new IOException("Invalid string length.");
	    }

	    int EID = input.readInt();
	    byte gameMode = input.readByte();
	    int dim = input.readInt();
	    byte difficulty = input.readByte();
	    byte maxPlayers = input.readByte();
	    
	    length = readVarInt(input);
	    
	    if (length == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    if (length == 0) {
	        throw new IOException("Invalid string length.");
	    }
	    
	    in = new byte[length];
	    input.readFully(in);  //read json string
	    String lvlType = new String(in);
	    
	    boolean debugInfo = input.readBoolean();
	    
	    System.out.println("Entity ID: " + EID);
	    System.out.println("Game Mode: " + gameMode);
	    System.out.println("Dimension: " + dim);
	    System.out.println("Difficulty: " + difficulty);
	    System.out.println("Max Players: " + maxPlayers);
	    System.out.println("Level Type: " + lvlType);
	    System.out.println("Debug Info: " + debugInfo);
	    
	    System.out.println();
	    
	    // S->C : Plugin Message (Optional)
	    size = readVarInt(input);
	    if (compressionEnabled) uncompressedSize = readVarInt(input);
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
	    
	    System.out.println("Identifier: " + json);
	    
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
	    
	    System.out.println("Data: " + json);
	    
	    System.out.println();
	    
	    // S->C : Server Difficulty (Optional)
	    size = readVarInt(input);
	    if (compressionEnabled) uncompressedSize = readVarInt(input);
	    packetId = readVarInt(input);

	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }
	    
	    difficulty = input.readByte();
	    
	    System.out.println("Difficulty: " + difficulty);
	    
	    System.out.println();
	    
	    // S->C : Spawn Position
	    size = readVarInt(input);
	    if (compressionEnabled) uncompressedSize = readVarInt(input);
	    packetId = readVarInt(input);

	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    int[] pos = readPos(input);
	    
	    System.out.println("X: " + pos[0]);
	    System.out.println("Y: " + pos[1]);
	    System.out.println("Z: " + pos[2]);
	    
	    System.out.println();
	    
	    // S->C : Player Abilities
	    size = readVarInt(input);
	    if (compressionEnabled) uncompressedSize = readVarInt(input);
	    packetId = readVarInt(input);

	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }
	    
	    /*Invulnerable	0x01
		Flying	0x02
		Allow Flying	0x04
		Creative Mode (Instant Break)	0x08
		*/
	    byte flags = input.readByte();
	    float flySpeed = input.readFloat();
	    float FOV = input.readFloat();
	    
	    System.out.println("Flag Value: " + flags);
	    if (getBit(flags, 0)) System.out.println("Invulnerability Enabled");
	    if (getBit(flags, 1)) System.out.println("Player is flying");
	    if (getBit(flags, 2)) System.out.println("Flying Enabled");
	    if (getBit(flags, 3)) System.out.println("Instant Break Enabled");
	    
	    System.out.println("Fly Speed: " + flySpeed);
	    System.out.println("FOV: " + FOV);
	    
	    System.out.println();
	    
	    // C->S : Plugin Message (Optional) follow up from server plugin messasge
	    buffer = new ByteArrayOutputStream();
	    
	    DataOutputStream message = new DataOutputStream(buffer);
	    message.writeByte(0x0A); //packet id
	    writeString(message, "minecraft:brand", StandardCharsets.UTF_8); // identifier
	    writeString(message, "vanilla", StandardCharsets.UTF_8); // data
	    
	    userInfo = buffer.toByteArray();

		writeVarInt(output, userInfo.length);
		if (compressionEnabled) output.writeByte(0x00); // uncompressed size
		output.write(userInfo);
	    
	    // C->S : Client Settings
	    buffer = new ByteArrayOutputStream();

	    DataOutputStream settings = new DataOutputStream(buffer);
	    settings.writeByte(0x04); //packet id
	    writeString(settings, "en_US", StandardCharsets.UTF_8);
	    settings.writeByte(0x07); // View distance
	    writeVarInt(settings, 0); // chat mode 0:enabled | 1:commands only | 2:hidden
	    settings.writeByte(0x01); // colors multiplayer setting
	    settings.writeByte(0x00); // displaying skin parts
	    writeVarInt(settings, 0); // Main hand 0:left | 1:right
	    
	    userInfo = buffer.toByteArray();

		writeVarInt(output, userInfo.length);
		if (compressionEnabled) output.writeByte(0x00); // uncompressed size
		output.write(userInfo);
		
	    // S->C : Player Position And Look
		size = readVarInt(input);
		if (compressionEnabled) uncompressedSize = readVarInt(input);
	    packetId = readVarInt(input);

	    if (packetId == -1) {
	        throw new IOException("Premature end of stream.");
	    }

	    double x = input.readDouble();
	    double y = input.readDouble();
	    double z = input.readDouble();
	    
	    float yaw = input.readFloat();
	    float pitch = input.readFloat();
	    
	    /* If set, value is relative
	      X	0x01
		  Y	0x02
		  Z	0x04
		  Y_ROT	0x08
		  X_ROT	0x10*/
	    flags = input.readByte();
	    boolean[] isRelative = getBits(flags);
	    
	    int teleportID = readVarInt(input);
	    
	    System.out.println("X: " + x);
	    System.out.println("Y: " + y);
	    System.out.println("Z: " + z);
	    System.out.println("Yaw: " + yaw);
	    System.out.println("pitch: " + pitch);
	    
	    System.out.println("Flag Value: " + flags);
	    if (isRelative[0]) System.out.println("X Position is relative");
	    if (isRelative[1]) System.out.println("Y Position is relative");
	    if (isRelative[2]) System.out.println("Z Position is relative");
	    if (isRelative[3]) System.out.println("Y Rotation (Pitch) is relative");
	    if (isRelative[4]) System.out.println("X Rotation (Yaw) is relative");
	    
	    System.out.println("Teleport ID: " + teleportID);
	    
	    System.out.println();
	    
	    // C->S : Teleport Confirm
	    buffer = new ByteArrayOutputStream();

	    DataOutputStream telConfirm = new DataOutputStream(buffer);
	    telConfirm.writeByte(0x00); // packet ID
	    writeVarInt(telConfirm, teleportID);
	    
	    userInfo = buffer.toByteArray();

		writeVarInt(output, userInfo.length);
		if (compressionEnabled) output.writeByte(0x00); // uncompressed size
		output.write(userInfo);
	    
	    // C->S : Player Position and Look
	    buffer = new ByteArrayOutputStream();

	    DataOutputStream position = new DataOutputStream(buffer);
	    position.writeByte(0x11); // Packet ID
	    position.writeDouble(x);
	    position.writeDouble(y - 1.62);
	    position.writeDouble(z);
	    
	    position.writeFloat(yaw);
	    position.writeFloat(pitch);
	    
	    position.writeBoolean(true); // onGround
	    
	    userInfo = buffer.toByteArray();

		writeVarInt(output, userInfo.length);
		if (compressionEnabled) output.writeByte(0x00); // uncompressed size
		output.write(userInfo);
	    
	    // C->S : Client Status
	    buffer = new ByteArrayOutputStream();

	    DataOutputStream status = new DataOutputStream(buffer);
	    status.writeByte(0x03); // Packet ID
	    writeVarInt(status, 0); // 0 = respawn | 1 = statistic menu opened
	    
	    userInfo = buffer.toByteArray();

		writeVarInt(output, userInfo.length);
		if (compressionEnabled) output.writeByte(0x00); // uncompressed size
		output.write(userInfo);
	    
	    // S->C : The rest of the data
		size = readVarInt(input);
		if (compressionEnabled) uncompressedSize = readVarInt(input);
	    
	    if (compressionEnabled && uncompressedSize >= 0)
	    {
	    	
	    }
	    
	    else
	    {
	    	packetId = readVarInt(input);
	    	
	    	System.out.println("Size: " + size);
		    System.out.format("Packet Id: 0x%02X%n", (byte) packetId);

		    if (packetId == -1) {
		        throw new IOException("Premature end of stream.");
		    }
	    }
	}
}