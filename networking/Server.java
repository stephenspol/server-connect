package networking;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;

import networking.buffer.*;
import networking.protocol.ClientboundManager;
import networking.protocol.Protocol;
import main.Main;

public class Server {

    private final Logger log = Logger.getLogger(Server.class.getName());
    private final ConsoleHandler consoleHandler = new ConsoleHandler();

    private String address;
    private int port;

    private InetSocketAddress host;

    public static final String PREMATURE = "Premature end of stream.";
	public static final String INVALID_LENGTH = "Invalid string length.";
	public static final String INVALID_PACKET = "Invalid packetID.";

    public Server (String address, int port) {
        log.setUseParentHandlers(false);
        log.addHandler(consoleHandler);

        log.setLevel(Level.FINER);
        consoleHandler.setLevel(Level.FINER);

        this.address = address;
        this.port = port;

        host = new InetSocketAddress(address, port);
    }
    
    public void statusPing(int timeout) throws IOException {
        log.info("Starting Status Ping!\n");

		try (Socket socket = new Socket()) { 

            log.fine("Attempting to connect to server...");
            socket.connect(host, timeout); // 3000 is standard for timeout
            log.info("Connection to server successful!\n");

			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

            log.log(Level.FINE, "Attempting handshake with {0}...", host.getAddress());
            byte[] handshakeMessage = Protocol.createHandshakeMessage(address, port, 1);

            // C->S : Handshake State=1
            buffer.writeVarInt(handshakeMessage.length);
            buffer.writeBytes(handshakeMessage);
			output.write(buffer.getBytes());
            log.fine("Handshake sent\n");

            // C->S : Request
            buffer.writeByte(0x01); //size is one
            buffer.writeByte(0x00); //packet id for ping
			output.write(buffer.getBytes());
            log.fine("Status request sent\n");

			Thread status = new Thread(new ClientboundManager(socket, 0));

			status.start();

            // C->S : Ping
            long now = System.currentTimeMillis();
            buffer.writeByte(0x09); //size of packet
            buffer.writeByte(0x01); //0x01 for ping
            buffer.writeLong(now); //time
			output.write(buffer.getBytes());
            log.fine("Pinging server...");

            while(status.isAlive()) {
				Thread.sleep(1000);
			}

            log.info("Status Ping Completed!\n");
        } catch (InterruptedException e) {
			log.log(Level.WARNING, "Thread Interrupted", e);
			
			Thread.currentThread().interrupt();
		}
    }

    public void connect(int timeout) throws IOException {

        Console console = System.console();

        Authentication user;
		String name;

        System.out.print("Username (Email if mitagated): ");
		String username = Main.sc.next();
		
		Main.sc.nextLine();
		
		char[] passArray = {};

		try
		{
			// Uses a array to store password to ensure security
			passArray = console.readPassword("Password: ");
		}

		catch (NullPointerException e)
		{
			log.log(Level.SEVERE, "Could not get Console instance! (Password is no longer secure)", e);

			System.out.print("Password: ");
			passArray = Main.sc.next().toCharArray();

			Main.sc.nextLine();
		}

		finally
		{
			user = new Authentication(username, passArray);
		}

		name = user.getName();

        log.info("Starting Login\n");

		try  (final Socket socket = new Socket())
		{
			log.fine("Attempting to connect to server...");
			socket.connect(host, timeout);
			log.info("Connection Completed!\n");

			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

			log.log(Level.FINE, "Attempting handshake with {0}...", host.getAddress());
			byte[] handshakeMessage = Protocol.createHandshakeMessage(address, port, 2);

			// C->S : Handshake State=2
			buffer.writeVarInt(handshakeMessage.length);
			buffer.writeBytes(handshakeMessage);
			output.write(buffer.getBytes());
			log.fine("Handshake sent\n");
			
			// C->S : Login Start
			MinecraftOutputBuffer string = new MinecraftOutputBuffer();
			string.writeByte(0x00); //packet id
			string.writeString(name, StandardCharsets.UTF_8);

			byte[] userInfo = string.getBytes();

			buffer.writeVarInt(userInfo.length);
			buffer.writeBytes(userInfo);

			output.write(buffer.getBytes());

			if (!address.equals("127.0.0.1"))
			{
				// S->C : Encryption Request

				// C->S : Encryption Response
			}
			
			Thread play = new Thread(new ClientboundManager(socket, 1));

			play.start();
			
			// C->S : Plugin Message (Optional) follow up from server plugin messasge
			/*buffer = new ByteArrayOutputStream();
			
			MinecraftOutputStream message = new MinecraftOutputStream(buffer);
			message.writeByte(0x0A); //packet id
			message.writeString("minecraft:brand", StandardCharsets.UTF_8); // identifier
			message.writeString("vanilla", StandardCharsets.UTF_8); // data
			
			userInfo = buffer.toByteArray();

			output.writeVarInt(userInfo.length);
			output.writeByte(0x00); // uncompressed size
			output.write(userInfo);
			
			// C->S : Client Settings
			buffer = new ByteArrayOutputStream();

			MinecraftOutputStream settings = new MinecraftOutputStream(buffer);
			settings.writeByte(0x04); //packet id
			settings.writeString("en_US", StandardCharsets.UTF_8);
			settings.writeByte(0x07); // View distance
			settings.writeVarInt(0); // chat mode 0:enabled | 1:commands only | 2:hidden
			settings.writeByte(0x01); // colors multiplayer setting
			settings.writeByte(0x00); // displaying skin parts
			settings.writeVarInt(0); // Main hand 0:left | 1:right
			
			userInfo = buffer.toByteArray();

			output.writeVarInt(userInfo.length);
			output.writeByte(0x00); // uncompressed size
			output.write(userInfo);
			
			// S->C : Player Position And Look
			/*size = input.readVarInt();
			packetId = input.readVarInt();

			if (packetId == -1) {
				log.severe(PREMATURE);
			}

			double x = input.readDouble();
			double y = input.readDouble();
			double z = input.readDouble();
			
			float yaw = input.readFloat();
			float pitch = input.readFloat();*/
			
			/* If set, value is relative
			X	0x01
			Y	0x02
			Z	0x04
			Y_ROT	0x08
			X_ROT	0x10*/
			/*byte flags = input.readByte();
			boolean[] isRelative = getBits(flags);
			
			int teleportID = input.readVarInt();

			log.fine("Player Position and Look\n");
			
			log.log(Level.FINE, "X: {0}", x);
			log.log(Level.FINE, "Y: {0}", y);
			log.log(Level.FINE, "Z: {0}", z);
			log.log(Level.FINE, "Yaw: {0}", yaw);
			log.log(Level.FINE, "pitch: {0}\n", pitch);
			
			log.log(Level.FINE, "Flag Value: {0}", flags);
			if (isRelative[0]) log.fine("X Position is relative");
			if (isRelative[1]) log.fine("Y Position is relative");
			if (isRelative[2]) log.fine("Z Position is relative");
			if (isRelative[3]) log.fine("Y Rotation (Pitch) is relative");
			if (isRelative[4]) log.fine("X Rotation (Yaw) is relative");
			
			log.log(Level.FINE, "\nTeleport ID: {0}\n", teleportID);
			
			// C->S : Teleport Confirm
			buffer = new ByteArrayOutputStream();

			MinecraftOutputStream telConfirm = new MinecraftOutputStream(buffer);
			telConfirm.writeByte(0x00); // packet ID
			telConfirm.writeVarInt(teleportID);
			
			userInfo = buffer.toByteArray();

			output.writeVarInt(userInfo.length);
			output.writeByte(0x00); // uncompressed size
			output.write(userInfo);
			
			// C->S : Player Position and Look
			buffer = new ByteArrayOutputStream();

			MinecraftOutputStream position = new MinecraftOutputStream(buffer);
			position.writeByte(0x11); // Packet ID
			position.writeDouble(x);
			position.writeDouble(y - 1.62);
			position.writeDouble(z);
			
			position.writeFloat(yaw);
			position.writeFloat(pitch);
			
			position.writeBoolean(true); // onGround
			
			userInfo = buffer.toByteArray();

			output.writeVarInt(userInfo.length);
			output.writeByte(0x00); // uncompressed size
			output.write(userInfo);
			
			// C->S : Client Status
			buffer = new ByteArrayOutputStream();

			MinecraftOutputStream status = new MinecraftOutputStream(buffer);
			status.writeByte(0x03); // Packet ID
			status.writeVarInt(0); // 0 = respawn | 1 = statistic menu opened
			
			userInfo = buffer.toByteArray();

			output.writeVarInt(userInfo.length);
			output.writeByte(0x00); // uncompressed size
			output.write(userInfo);
			
			// S->C : The rest of the data
			/*size = Protocol.readVarInt(input);
			uncompressedSize = Protocol.readVarInt(input);
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
			
			packetId = Protocol.readVarInt(uncompressedData);
			
			log.log(Level.FINE, "Size: {0}", size);
			log.log(Level.FINE, "Uncompressed Size: {0}", uncompressedSize);
			System.out.print("Packet Id: " );
			System.out.format("0x%02X", (byte) packetId);
			System.out.println();

			if (packetId == -1) {
				log.severe(PREMATURE);
			}*/

			// Keep main thread stuck in a loop to not kill socket while other thread is running
			while(play.isAlive()) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			log.log(Level.WARNING, "Thread Interrupted", e);
			
			Thread.currentThread().interrupt();
		}

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
