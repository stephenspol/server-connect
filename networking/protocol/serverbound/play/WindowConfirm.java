package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x07 | C->S
public class WindowConfirm {

    private WindowConfirm() {}

    public static byte[] execute(byte windowID, short actionNum, boolean accepted) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x07;

        buffer.writeByte(packetID);

        buffer.writeByte(windowID);

        buffer.writeShort(actionNum);

        buffer.writeBoolean(accepted);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Window ID: {0}, Action Number: {1}, Accepted: {2}", new Object[]{windowID, actionNum, accepted});

        return buffer.getPacket();
    }
    
}