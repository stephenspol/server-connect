package networking.protocol;

import java.io.*;
import java.nio.charset.StandardCharsets;

import networking.stream.*;

public final class Protocol {

    private Protocol() {}

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
