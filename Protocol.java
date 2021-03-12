import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import NBT.CompoundTag;

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

	// Packet ID 0x24 | S->C
	public static void joinGame(DataInputStream input) throws IOException {
			int EID = input.readInt();
			boolean isHardcore = input.readBoolean();
			int gameMode = input.readUnsignedByte();
			byte prevGameMode = input.readByte();

			int worldCount = readVarInt(input);

			String[] worldNames = new String[worldCount];

			for (int i = 0; i < worldNames.length; i++)
			{
				worldNames[i] = readString(input);
			}

			int dim = input.readInt();
			byte difficulty = input.readByte();
			byte maxPlayers = input.readByte();
			
			
			String lvlType = readString(input);
			
			boolean debugInfo = input.readBoolean();
			
			log.log(Level.FINE, "Entity ID: {0}", EID);
			log.log(Level.FINE, "Game Mode: {0}", gameMode);
			log.log(Level.FINE, "Dimension: {0}", dim);
			log.log(Level.FINE, "Difficulty: {0}", difficulty);
			log.log(Level.FINE, "Max Players: {0}", maxPlayers);
			log.log(Level.FINE, "Level Type: {0}", lvlType);
			log.log(Level.FINE, "Debug Info: {0}\n", debugInfo);
	}

    public static byte[] createHandshakeMessage(String host, int port, int state) throws IOException {
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    DataOutputStream handshake = new DataOutputStream(buffer);
	    handshake.writeByte(0x00); //packet id for handshake
	    writeVarInt(handshake, (state == 1)? 4 : 754); //status version = 4, minecraft version = 754(1.16.5)
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

	public static String readString(DataInputStream in) throws IOException {
		int length = readVarInt(in);

		if (length == -1) {
			throw new IOException(Server.PREMATURE);
		}

		else if (length == 0) {
			throw new IOException(Server.INVALID_LENGTH);
		}

		byte[] input = new byte[length];
		in.readFully(input);  //read json string

		return new String(input);
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
	        if (j > 5) throw new IOException("VarInt too big");
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

	// WIP
	public static CompoundTag readNBT(DataInputStream in) throws IOException {
		CompoundTag nbt;

		return nbt;
	}

}
