package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x11 | C->S
public class LockDifficulty {

    private LockDifficulty() {}

    public static byte[] execute(boolean locked) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x11;

        buffer.writeByte(packetID);

        buffer.writeBoolean(locked);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Locked: {0}", locked);

        return buffer.getPacket();
    }
    
}