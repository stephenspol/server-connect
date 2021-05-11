package networking.protocol.serverbound.handshake;

import networking.buffer.MinecraftOutputBuffer;

import java.nio.charset.StandardCharsets;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x00 | C->S
public class Handshake {

    private Handshake() {}

    /**
     * Sent out in the start of any connection to server
     * @param host Hostname or IP Address (Cannot exceed <b>255</b> in size).
     * @param port Server Port Number, Default is <i>25565</i>.
     * @param state 1 for Status, 2 for Login.
     * @return Stream of bytes ready to be sent out to server
     */
    public static byte[] execute(String host, int port, int state) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x00;
        
        buffer.writeByte(packetID);
	    
        //status version = 4, minecraft version = 754(1.16.5)
        int version = (state == 1)? 4 : 754;
        
        buffer.writeVarInt(version); 

	    buffer.writeString(host, StandardCharsets.UTF_8);
	    buffer.writeShort(port);
	    buffer.writeVarInt(state);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Version: {0}", version);

        log.log(packetInfo, "Host: {0}, Port: {1}, State: {2}", new Object[]{host, port, state});

	    return buffer.getPacket();
    }
    
}