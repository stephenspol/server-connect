package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x21 | C->S
public class ResourcePackStatus {

    private ResourcePackStatus() {}

    public static byte[] execute(int result) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x21;

        buffer.writeByte(packetID);

        buffer.writeVarInt(result);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Result: {0}", result);

        return buffer.getPacket();
    }
    
}