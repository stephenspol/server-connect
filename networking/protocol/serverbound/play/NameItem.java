package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x20 | C->S
public class NameItem {

    private NameItem() {}

    public static byte[] execute(String name) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x20;

        buffer.writeByte(packetID);

        buffer.writeString(name);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Item Name: {0}", name);

        return buffer.getPacket();
    }
    
}