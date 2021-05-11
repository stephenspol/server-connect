package networking.protocol.serverbound.status;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x01 | C->S
public class Ping {

    private Ping() {}

    public static byte[] execute() {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        long now = System.currentTimeMillis();

        byte packetID = 0x01;

        buffer.writeByte(packetID);

        buffer.writeLong(now);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Current time: {0}", now);

        return buffer.getPacket();
    }
    
}