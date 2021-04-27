package networking.buffer;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class MinecraftOutputBuffer {

	private final ArrayList<Byte> buffer;
    
    public MinecraftOutputBuffer() {
        buffer = new ArrayList<>();
    }

	public final void writeBytes(byte[] bytes) {
		for (Byte b : bytes) {
			buffer.add(b);
		}
	}

	public final byte[] getBytes() {
		byte[] bytes = new byte[buffer.size()];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = buffer.get(i);
		}

		buffer.clear();

		return bytes;
	}

	public final void writeByte(byte b) {
		buffer.add(b);
	}

	public final void writeByte(int b) {
		buffer.add((byte) (b & 0xFF));
	}

	public final void writeShort(short value) {
		writeByte(value >>> 8);
        writeByte(value);
	}

	public final void writeShort(int value) {
		writeShort((short) value);
	}

	public final void writeInt(int value) {
		writeByte(value >>> 24);
        writeByte(value >>> 16);
        writeByte(value >>> 8);
        writeByte(value);

	}

	public final void writeLong(long value) {
		writeInt((int) (value >> 32));
        writeInt((int) (value));
	}

    public final void writeString(String string, Charset charset) {
	    byte[] bytes = string.getBytes(charset);
	    writeVarInt(bytes.length);
	    writeBytes(bytes);
	}

    public final void writeVarInt(int paramInt) {
	    while (true) {
	        if ((paramInt & 0xFFFFFF80) == 0) {
	          writeByte(paramInt);
	          return;
	        }

	        writeByte(paramInt & 0x7F | 0x80);
	        paramInt >>>= 7;
	    }
	}

    public final void writePos(int[] pos) {
		if (pos.length != 3) {
			throw new IllegalArgumentException("Position array is not the length of 3!");
		}

		writeLong(((pos[0] & 0x3FFFFFF) << 38) | ((pos[1] & 0xFFF) << 26) | (pos[2] & 0x3FFFFFF));
	}
}
