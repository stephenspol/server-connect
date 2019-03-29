import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;


/* Packet Format:
 * 1st value is size of payload with a type of variable integer
 * if compression is enabled, next value is of type variable integer and is uncompressed size of payload
 * if compression is enabled, next value is a compressed payload of size of first value
 * else next value is payload of size of first value
 * 
 * String Format:
 * 
 */
public class Packet 
{
	
	// 1 == status | 2 == login | 3 == play
	public static byte state = 1;
	public static int compressionThreshold = -1;
	
	public static Object[] readPacket(DataInputStream input) throws IOException
	{
		DataInputStream payloadStream;
		Object[] payloadArray = {};
		
		int size = readVarInt(input);
		int uncompressedSize = 0;
		if (compressionThreshold >= 0) uncompressedSize = readVarInt(input);
		
		// if uncompressedSize == 0, data is not compressed
		if (uncompressedSize > 0)
		{
			payloadStream = decompressZlib(input, size, uncompressedSize);
		}
		
		else
		{
			byte[] payload = new byte[size];
			input.readFully(payload);
			payloadStream = new DataInputStream(new ByteArrayInputStream(payload));
		}
		
		int packetId = readVarInt(payloadStream);
		
		switch (state)
		{
			case 1:
				switch (packetId)
				{
					case 0:
						payloadArray[0] = response(input);
						break;
					case 1:
						
						break;
					default:
						throw new RuntimeException(packetId + " is a Invalid Packet ID");
				}
				break;
			case 2:
				switch (packetId)
				{
				
				}
				break;
			case 3:
				switch (packetId)
				{
				
				}
				break;
		}
		
		return payloadArray;
	}
	
	public static String response(DataInputStream input) throws IOException
	{
	    return readString(input);
	}
	
	public static DataInputStream decompressZlib(DataInputStream input, int size, int uncompressedSize) throws IOException
	{
		int resultLength;
	    byte[] result = new byte[uncompressedSize];
	    byte[] compressedData = new byte[size];
	    
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
        
        return new DataInputStream(new ByteArrayInputStream(result));
	}
	
	public static byte [] createHandshakeMessage(String host, int port, int state) throws IOException {
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    DataOutputStream handshake = new DataOutputStream(buffer);
	    handshake.writeByte(0x00); //packet id for handshake
	    writeVarInt(handshake, (state == 1)? 4 : 404); //status version = 4, minecraft version = 404(1.13.2)
	    writeString(handshake, host, StandardCharsets.UTF_8);
	    handshake.writeShort(port); //port
	    writeVarInt(handshake, state); //state

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
	        throw new IOException("Premature end of stream.");
	    }

	    if (length == 0) {
	        throw new IOException("Invalid string length.");
	    }

	    byte[] buffer = new byte[length];
	    in.readFully(buffer);  //read json string
	    return new String(buffer);
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
	
	public static int[] readPos(DataInputStream in) throws IOException {
		long val = in.readLong();
		
		int x = (int)(val >> 38); // 26 MSBs
		int y = (int)((val >> 26) & 0xFFF); // 12 bits between them
		int z = (int)(val << 38 >> 38); // 26 LSBs
		
		if (x >= Math.pow(2, 25)) x -= Math.pow(2, 26);
		if (y >= Math.pow(2, 11)) y -= Math.pow(2, 12);
		if (z >= Math.pow(2, 25)) z -= Math.pow(2, 26);
		
		int[] pos = {x, y, z};	
		
		return pos;
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
