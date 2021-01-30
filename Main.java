import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Main {
	
	// Protocol states Status | Login | Play
	public static void main(String[] args) throws IOException {
		String address;
		int port;
		
		int data;

		String username;

		final String PREMATURE = "Premature end of stream.";
		final String INVALID_LENGTH = "Invalid string length.";
		final String INVALID_PACKET = "Invalid packetID.";

		// More effecient way of printing to console
		Logger log = Logger.getLogger(Main.class.getName());
		
		Scanner sc = new Scanner(System.in);
		Console console = System.console();

		System.out.print("Server Address: ");
		address = sc.nextLine();
		System.out.print("Port: ");
		port = sc.nextInt();

		System.out.print("Username: ");
		username = sc.next();
		
		sc.nextLine();
		
		char[] passArray = {};

		try
		{
			// Uses a array to store password to ensure security
			passArray = console.readPassword("Password: ");
		}

		catch (NullPointerException e)
		{
			log.severe("Couldn't get Console instance! (Password is no longer secure)");

			System.out.print("Password: ");
			passArray = sc.next().toCharArray();

			sc.nextLine();
		}

		finally
		{
			new Authentication(username, passArray);
		}

		sc.close();
		
		InetSocketAddress host = new InetSocketAddress(address, port);

		log.info("Starting Status Ping!\n");

		try (Socket socket = new Socket())
		{
			log.fine("Attempting to connect to server...");
			socket.connect(host, 3000);
			log.info("Connection to server successful!\n");

			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			DataInputStream input = new DataInputStream(socket.getInputStream());

			log.log(Level.FINE, "Attempting handshake with {}...", host.getAddress());
			byte[] handshakeMessage = createHandshakeMessage(address, port, 1);

			// C->S : Handshake State=1
			writeVarInt(output, handshakeMessage.length);
			output.write(handshakeMessage);
			log.fine("Handshake sent\n");

			// C->S : Request
			output.writeByte(0x01); //size is one
			output.writeByte(0x00); //packet id for ping
			log.fine("Status request sent\n");

			// S->C : Response
			int size = readVarInt(input);
			int packetId = readVarInt(input);

			if (packetId == -1) {
				log.severe(PREMATURE);
			}

			else if (packetId != 0x00) { //we want a status response
				log.severe(INVALID_PACKET);
			}
			int length = readVarInt(input); //length of json string

			if (length == -1) {
				log.severe(PREMATURE);
			}

			else if (length == 0) {
				log.severe(INVALID_LENGTH);
			}

			byte[] in = new byte[length];
			input.readFully(in);  //read json string
			String json = new String(in);

			// C->S : Ping
			long now = System.currentTimeMillis();
			output.writeByte(0x09); //size of packet
			output.writeByte(0x01); //0x01 for ping
			output.writeLong(now); //time
			log.fine("Pinging server...");

			// S->C : Pong
			readVarInt(input);
			packetId = readVarInt(input);

			if (packetId == -1) {
				log.severe(PREMATURE);
			}

			else if (packetId != 0x01) {
				log.severe(INVALID_PACKET);
			}

			long pingtime = input.readLong(); //read response

			log.log(Level.FINE, "Pong time: {}ms\n", pingtime);
			// print out server info
			log.log(Level.FINER, "Server response: {}\n", json);

			log.info("Status Ping Completed!\n");
		}	

		log.info("Starting Login\n");

		try (Socket socket = new Socket())
		{
			log.fine("Attempting to connect to server...");
			socket.connect(host, 3000);
			log.info("Connection Completed!\n");

			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			DataInputStream input = new DataInputStream(socket.getInputStream());

			log.log(Level.FINE, "Attempting handshake with {}...", host.getAddress());
			byte[] handshakeMessage = createHandshakeMessage(address, port, 2);

			// C->S : Handshake State=2
			writeVarInt(output, handshakeMessage.length);
			output.write(handshakeMessage);
			log.fine("Handshake sent\n");
			
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

			if (!address.equals("127.0.0.1"))
			{
				// S->C : Encryption Request

				// C->S : Encryption Response
			}
			
			// S->C : Set Compression (optional)
			int size = readVarInt(input);
			int packetId = readVarInt(input);

			if (packetId == -1) {
				log.severe(PREMATURE);
			}
			
			if (size == -1) {
				log.severe(PREMATURE);
			}

			if (size == 0) {
				log.severe(INVALID_LENGTH);
			}
			
			data = readVarInt(input);
			
			log.log(Level.FINE, "Compression size: {}", data);
			
			// if received set compression packet and is not a negative number, enable compression

			
			// S->C : Login Success
			size = readVarInt(input);
			int uncompressedSize = readVarInt(input); // if 0, data is not compressed
			packetId = readVarInt(input);
			
			if (packetId == -1) {
				log.severe(PREMATURE);
			}

			int length = readVarInt(input); //length of json string
			
			if (length == -1) {
				log.severe(PREMATURE);
			}

			if (length == 0) {
				log.severe(INVALID_LENGTH);
			}

			byte[] in = new byte[length];
			input.readFully(in);  //read json string
			String json = new String(in);

			log.log(Level.INFO, "Player UUID: {}", json);
			
			length = readVarInt(input); //length of json string

			if (length == -1) {
				log.severe(PREMATURE);
			}

			if (length == 0) {
				log.severe(INVALID_LENGTH);
			}

			in = new byte[length];
			input.readFully(in);  //read json string
			json = new String(in);
			
			log.log(Level.INFO, "Username: {}\n", json);
			
			// S->C : Join Game
			log.info("Joined Game!\n");
			size = readVarInt(input);
			uncompressedSize = readVarInt(input);
			packetId = readVarInt(input);

			if (packetId == -1) {
				log.severe(PREMATURE);
			}

			if (size == -1) {
				log.severe(PREMATURE);
			}

			if (size == 0) {
				log.severe(INVALID_LENGTH);
			}

			int EID = input.readInt();
			byte gameMode = input.readByte();
			int dim = input.readInt();
			byte difficulty = input.readByte();
			byte maxPlayers = input.readByte();
			
			length = readVarInt(input);
			
			if (length == -1) {
				log.severe(PREMATURE);
			}

			if (length == 0) {
				log.severe(INVALID_LENGTH);
			}
			
			in = new byte[length];
			input.readFully(in);  //read json string
			String lvlType = new String(in);
			
			boolean debugInfo = input.readBoolean();
			
			log.log(Level.FINE, "Entity ID: {}", EID);
			log.log(Level.FINE, "Game Mode: {}", gameMode);
			log.log(Level.FINE, "Dimension: {}", dim);
			log.log(Level.FINE, "Difficulty: {}", difficulty);
			log.log(Level.FINE, "Max Players: {}", maxPlayers);
			log.log(Level.FINE, "Level Type: {}", lvlType);
			log.log(Level.FINE, "Debug Info: {}\n", debugInfo);
			
			// S->C : Plugin Message (Optional)
			size = readVarInt(input);
			uncompressedSize = readVarInt(input);
			packetId = readVarInt(input);
			
			if (packetId == -1) {
				log.severe(PREMATURE);
			}

			length = readVarInt(input); //length of json string

			if (length == -1) {
				log.severe(PREMATURE);
			}

			if (length == 0) {
				log.severe(INVALID_LENGTH);
			}
			
			in = new byte[length];
			input.readFully(in);  //read json string
			json = new String(in);
			
			log.log(Level.FINE, "Identifier: {}", json);
			
			length = readVarInt(input); //length of json string

			if (length == -1) {
				log.severe(PREMATURE);
			}

			if (length == 0) {
				log.severe(INVALID_LENGTH);
			}
			
			in = new byte[length];
			input.readFully(in);  //read json string
			json = new String(in);
			
			log.log(Level.FINE, "Data: {}\n", json);
			
			// S->C : Server Difficulty (Optional)
			size = readVarInt(input);
			uncompressedSize = readVarInt(input);
			packetId = readVarInt(input);

			if (packetId == -1) {
				log.severe(PREMATURE);
			}
			
			difficulty = input.readByte();
			
			log.log(Level.FINE, "Server Difficulty: {}\n", difficulty);
			
			// S->C : Spawn Position
			size = readVarInt(input);
			uncompressedSize = readVarInt(input);
			packetId = readVarInt(input);

			if (packetId == -1) {
				log.severe(PREMATURE);
			}

			int[] pos = readPos(input);
			
			log.fine("Spawn Position\n");

			log.log(Level.FINE, "X: {}", pos[0]);
			log.log(Level.FINE, "Y: {}", pos[1]);
			log.log(Level.FINE, "Z: {}\n", pos[2]);
			
			// S->C : Player Abilities
			size = readVarInt(input);
			uncompressedSize = readVarInt(input);
			packetId = readVarInt(input);

			if (packetId == -1) {
				log.severe(PREMATURE);
			}
			
			/*Invulnerable	0x01
			Flying	0x02
			Allow Flying	0x04
			Creative Mode (Instant Break)	0x08
			*/
			byte flags = input.readByte();
			float flySpeed = input.readFloat();
			float FOV = input.readFloat();
			
			log.log(Level.FINE, "Flag Value: {}", flags);
			if (getBit(flags, 0)) log.fine("Invulnerability Enabled");
			if (getBit(flags, 1)) log.fine("Player is flying");
			if (getBit(flags, 2)) log.fine("Flying Enabled");
			if (getBit(flags, 3)) log.fine("Instant Break Enabled");
			
			log.log(Level.FINE, "Fly Speed: {}", flySpeed);
			log.log(Level.FINE, "FOV: {}\n", FOV);
			
			// C->S : Plugin Message (Optional) follow up from server plugin messasge
			buffer = new ByteArrayOutputStream();
			
			DataOutputStream message = new DataOutputStream(buffer);
			message.writeByte(0x0A); //packet id
			writeString(message, "minecraft:brand", StandardCharsets.UTF_8); // identifier
			writeString(message, "vanilla", StandardCharsets.UTF_8); // data
			
			userInfo = buffer.toByteArray();

			writeVarInt(output, userInfo.length);
			output.writeByte(0x00); // uncompressed size
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
			output.writeByte(0x00); // uncompressed size
			output.write(userInfo);
			
			// S->C : Player Position And Look
			size = readVarInt(input);
			packetId = readVarInt(input);

			if (packetId == -1) {
				log.severe(PREMATURE);
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

			log.fine("Player Position and Look\n");
			
			log.log(Level.FINE, "X: {}", x);
			log.log(Level.FINE, "Y: {}", y);
			log.log(Level.FINE, "Z: {}", z);
			log.log(Level.FINE, "Yaw: {}", yaw);
			log.log(Level.FINE, "pitch: {}\n", pitch);
			
			log.log(Level.FINE, "Flag Value: {}", flags);
			if (isRelative[0]) log.fine("X Position is relative");
			if (isRelative[1]) log.fine("Y Position is relative");
			if (isRelative[2]) log.fine("Z Position is relative");
			if (isRelative[3]) log.fine("Y Rotation (Pitch) is relative");
			if (isRelative[4]) log.fine("X Rotation (Yaw) is relative");
			
			log.log(Level.FINE, "\nTeleport ID: {}\n", teleportID);
			
			// C->S : Teleport Confirm
			buffer = new ByteArrayOutputStream();

			DataOutputStream telConfirm = new DataOutputStream(buffer);
			telConfirm.writeByte(0x00); // packet ID
			writeVarInt(telConfirm, teleportID);
			
			userInfo = buffer.toByteArray();

			writeVarInt(output, userInfo.length);
			output.writeByte(0x00); // uncompressed size
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
			output.writeByte(0x00); // uncompressed size
			output.write(userInfo);
			
			// C->S : Client Status
			buffer = new ByteArrayOutputStream();

			DataOutputStream status = new DataOutputStream(buffer);
			status.writeByte(0x03); // Packet ID
			writeVarInt(status, 0); // 0 = respawn | 1 = statistic menu opened
			
			userInfo = buffer.toByteArray();

			writeVarInt(output, userInfo.length);
			output.writeByte(0x00); // uncompressed size
			output.write(userInfo);
			
			// S->C : The rest of the data
			size = readVarInt(input);
			uncompressedSize = readVarInt(input);
			int resultLength;
			byte[] result = new byte[uncompressedSize];
			byte[] compressedData = new byte[size];
			if (uncompressedSize > 0)
			{
				input.readFully(compressedData);
				
				//for (byte b: compressedData) System.out.print(b + " ");
				
				Inflater decompresser = new Inflater();
				decompresser.setInput(compressedData, 0, size);
				try {
					resultLength = decompresser.inflate(result);
					
					//if (resultLength != uncompressedSize) throw new RuntimeException("Uncompression failed! Length not equal");
				} catch (DataFormatException e) {
					e.printStackTrace();
				}
				decompresser.end();
			}
			DataInputStream uncompressedData = new DataInputStream(new ByteArrayInputStream(result));
			
			packetId = readVarInt(uncompressedData);
			
			System.out.println("Size: " + size);
			System.out.println("Uncompressed Size: " + uncompressedSize);
			System.out.print("Packet Id: " );
			System.out.format("0x%02X", (byte) packetId);
			System.out.println();

			if (packetId == -1) {
				log.severe(PREMATURE);
			}
		}
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
	        if (j > 5) Logger.getLogger(Main.class.getName()).severe("VarInt too big");
	        if ((k & 0x80) != 128) break;
	    }
	    return i;
	}
	
	public static int[] readPos(DataInputStream in) throws IOException {
		long val = in.readLong();
		
		int x = (int)(val >> 38); // 26 MSBs
		int y = (int)((val >> 26) & 0xFFF); // 12 bits between them
		int z = (int)(val << 38 >> 38); // 26 LSBs
		
		if (x >= Math.pow(2, 25)) x -= Math.pow(2, 26);
		if (y >= Math.pow(2, 11)) y -= Math.pow(2, 12);
		if (z >= Math.pow(2, 25)) z -= Math.pow(2, 26);
		
		return new int[]{x, y, z};
	}
	
	public static void writePos(DataOutputStream out, int[] pos) throws IOException {
		out.writeLong(((pos[0] & 0x3FFFFFF) << 38) | ((pos[1] & 0xFFF) << 26) | (pos[2] & 0x3FFFFFF));
	}
	
	public static boolean getBit(byte num, int pos)
	{
		return ((num >> pos) & 1) == 1;
	}
	
	public static boolean[] getBits(byte num)
	{
		boolean[] bits = new boolean[7];
		
		for (int i = 0; i < bits.length; i++) bits[i] = getBit(num, i);
		
		return bits;
	}
}