package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x1B | C->S
public class PlayerDigging {

    private PlayerDigging() {}

    public static byte[] execute(int status, int[] pos, byte face) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x1B;

        buffer.writeByte(packetID);

        buffer.writeVarInt(status);

        buffer.writePos(pos);

        buffer.writeByte(face);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Status: {0}, X: {1}, Y: {2}, Z: {3}, Face: {4}", new Object[]{status, pos[0], pos[1], pos[2], face});

        return buffer.getPacket();
    }
    
}