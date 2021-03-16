import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import NBT.Tag;

public final class Protocol {

	private static final Logger log = Logger.getLogger(Protocol.class.getName());
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}
    
    private Protocol() {}

	// Packet ID 0x0D | S->C
	public static void serverDifficulty(MinecraftInputStream in) throws IOException {
		int difficulty = in.readUnsignedByte();
			
		log.log(Level.FINE, "Server Difficulty: {0}", difficulty);

		boolean locked = in.readBoolean();

		log.log(Level.FINE, "Difficulty Locked: {0}\n", locked);
	}

	// Packet ID 0x17 | S->C
	public static void pluginMessage(MinecraftInputStream in) throws IOException {
		String channel = in.readString();
			
		log.log(Level.FINE, "Identifier: {0}", channel);
			
		String data = in.readString();
			
		log.log(Level.FINE, "Data: {0}\n", data);
	}

	// Packet ID 0x24 | S->C
	public static void joinGame(MinecraftInputStream in) throws IOException {
			int EID = in.readInt();
			boolean isHardcore = in.readBoolean();
			int gameMode = in.readUnsignedByte();
			byte prevGameMode = in.readByte();

			int worldCount = in.readVarInt();

			String[] worldNames = new String[worldCount];

			for (int i = 0; i < worldNames.length; i++)
			{
				worldNames[i] = in.readString();
			}

			Tag<?> dimCodec = in.readNBT();
			Tag<?> dim = in.readNBT();

			String worldName = in.readString();
			long hashedSeed = in.readLong();

			int maxPlayers = in.readVarInt();
			int viewDist = in.readVarInt();
			
			boolean reducedDebug = in.readBoolean();
			boolean enableRespawnScr = in.readBoolean();
			boolean isDebug = in.readBoolean();

			boolean isFlat = in.readBoolean();
			
			log.log(Level.FINE, "Entity ID: {0}", EID);
			log.log(Level.FINE, "Is Hardcore: {0}", isHardcore);
			log.log(Level.FINE, "Game Mode: {0}", gameMode);
			log.log(Level.FINE, "Prev Game Mode: {0}", prevGameMode);
			log.log(Level.FINE, "Dimension Codec: {0}", dimCodec);
			log.log(Level.FINE, "Dimension: {0}", dim);
			log.log(Level.FINE, "World Name: {0}", worldName);
			log.log(Level.FINE, "Hashed seed: {0}", hashedSeed);
			log.log(Level.FINE, "Max Players: {0}", maxPlayers);
			log.log(Level.FINE, "View Distance: {0}", viewDist);
			log.log(Level.FINE, "Reduced Debug Info: {0}", reducedDebug);
			log.log(Level.FINE, "Enable Respawn Screen: {0}", enableRespawnScr);
			log.log(Level.FINE, "Is Debug Enabled: {0}", isDebug);
			log.log(Level.FINE, "Is a Flat World: {0}\n", isFlat);
	}

    public static byte[] createHandshakeMessage(String host, int port, int state) throws IOException {
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    MinecraftOutputStream handshake = new MinecraftOutputStream(buffer);
	    handshake.writeByte(0x00); //packet id for handshake
	    handshake.writeVarInt((state == 1)? 4 : 754); //status version = 4, minecraft version = 754(1.16.5)
	    handshake.writeString(host, StandardCharsets.UTF_8);
	    handshake.writeShort(port); //port
	    handshake.writeVarInt(state); //state (1 for status, 2 for login)

	    return buffer.toByteArray();
	}

}
