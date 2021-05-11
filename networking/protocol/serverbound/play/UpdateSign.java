package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x2B | C->S
public class UpdateSign {

    private UpdateSign() {}

    public static byte[] execute(int[] pos, String line1, String line2, String line3, String line4) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x2B;

        buffer.writeByte(packetID);

        buffer.writePos(pos);

        buffer.writeString(line1);
        buffer.writeString(line2);
        buffer.writeString(line3);
        buffer.writeString(line4);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});

        log.log(packetInfo, "Line 1: {0}", line1);
        log.log(packetInfo, "Line 2: {0}", line2);
        log.log(packetInfo, "Line 3: {0}", line3);
        log.log(packetInfo, "Line 4: {0}", line4);

        return buffer.getPacket();
    }
    
}