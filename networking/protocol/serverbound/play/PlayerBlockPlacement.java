package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x2E | C->S
public class PlayerBlockPlacement {

    private PlayerBlockPlacement() {}

    public static byte[] execute(int hand, int[] pos, int face, float cursorX, float cursorY, float cursorZ, boolean insideBlock) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x2E;

        buffer.writeByte(packetID);

        buffer.writeVarInt(hand);

        buffer.writePos(pos);

        buffer.writeVarInt(face);

        buffer.writeFloat(cursorX);
        buffer.writeFloat(cursorY);
        buffer.writeFloat(cursorZ);

        buffer.writeBoolean(insideBlock);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Hand: {0}, X: {1}, Y: {2}, Z: {3}", new Object[]{hand, pos[0], pos[1], pos[2]});

        log.log(packetInfo, "Face: {0}, Cursor X: {1}, Cursor Y: {2}, Cursor Z: {3}", new Object[]{face, cursorX, cursorY, cursorZ});

        log.log(packetInfo, "Inside Block: {0}", insideBlock);

        return buffer.getPacket();
    }
    
}