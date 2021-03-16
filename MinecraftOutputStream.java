import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class MinecraftOutputStream extends DataOutputStream {
    
    public MinecraftOutputStream(OutputStream out) {
        super(out);
    }

    public final void writeString(String string, Charset charset) throws IOException {
	    byte[] bytes = string.getBytes(charset);
	    writeVarInt(bytes.length);
	    write(bytes);
	}

    public final void writeVarInt(int paramInt) throws IOException {
	    while (true) {
	        if ((paramInt & 0xFFFFFF80) == 0) {
	          writeByte(paramInt);
	          return;
	        }

	        writeByte(paramInt & 0x7F | 0x80);
	        paramInt >>>= 7;
	    }
	}

    public final void writePos(int[] pos) throws IOException {
		if (pos.length != 3) {
			throw new IllegalArgumentException("Position array is not the length of 3!");
		}

		writeLong(((pos[0] & 0x3FFFFFF) << 38) | ((pos[1] & 0xFFF) << 26) | (pos[2] & 0x3FFFFFF));
	}
}
