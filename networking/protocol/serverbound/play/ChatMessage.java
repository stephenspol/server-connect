package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x03 | C->S
public class ChatMessage {

    private ChatMessage() {}

    public static byte[] execute(String message) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x03;

        buffer.writeByte(packetID);

        buffer.writeString(message);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Message: {0}", message);

        return buffer.getPacket();
    }
    
}