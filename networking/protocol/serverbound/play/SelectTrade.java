package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x23 | C->S
public class SelectTrade {

    private SelectTrade() {}

    public static byte[] execute(int slot) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x23;

        buffer.writeByte(packetID);

        buffer.writeVarInt(slot);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Selected Slot: {0}", slot);

        return buffer.getPacket();
    }
    
}