package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x25 | C->S
public class HeldItemChange {

    private HeldItemChange() {}

    public static byte[] execute(short slot) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x25;

        buffer.writeByte(packetID);

        buffer.writeShort(slot);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Slot: {0}", slot);

        return buffer.getPacket();
    }
    
}