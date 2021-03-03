import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Protocol {
    
    private Protocol() {}

    public static byte [] createHandshakeMessage(String host, int port, int state) throws IOException {
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

}
