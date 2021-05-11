package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x2F | C->S
public class UseItem {

    private UseItem() {}

    public static byte[] execute(int hand) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x2F;

        buffer.writeByte(packetID);

        buffer.writeVarInt(hand);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Hand: {0}", (hand == 0) ? "Main Hand" : "Off Hand");

        return buffer.getPacket();
    }
    
}