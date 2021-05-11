package networking.protocol.serverbound.status;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x00 | C->S
public class Request {

    private Request() {}

    public static byte[] execute() {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();
    
        byte packetID = 0x00;

        buffer.writeByte(packetID);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        return buffer.getPacket();
    }
    
}