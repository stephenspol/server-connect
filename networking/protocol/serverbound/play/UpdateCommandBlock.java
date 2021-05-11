package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x26 | C->S
public class UpdateCommandBlock {

    private UpdateCommandBlock() {}

    public static byte[] execute(int[] pos, String command, int mode, byte flags) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x26;

        buffer.writeByte(packetID);

        buffer.writePos(pos);

        buffer.writeString(command);

        buffer.writeVarInt(mode);
        buffer.writeByte(flags);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});

        log.log(packetInfo, "Command: {0}", command);

        log.log(packetInfo, "Mode: {0}, Flags: {1}", new Object[]{mode, flags});

        return buffer.getPacket();
    }
    
}