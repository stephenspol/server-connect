package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x08 | C->S
public class ClickWindowButton {

    private ClickWindowButton() {}

    public static byte[] execute(byte windowID, byte buttonID) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x08;

        buffer.writeByte(packetID);

        buffer.writeByte(windowID);

        buffer.writeByte(buttonID);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Window ID: {0}, Button ID: {1}", new Object[]{windowID, buttonID});

        return buffer.getPacket();
    }
    
}