package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x15 | C->S
public class PlayerMove {

    private PlayerMove() {}

    public static byte[] execute(boolean onGround) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x03;

        buffer.writeByte(packetID);

        buffer.writeBoolean(onGround);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "On Ground: {0}", onGround);

        return buffer.getPacket();
    }
    
}