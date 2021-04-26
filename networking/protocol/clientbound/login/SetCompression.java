package networking.protocol.clientbound.login;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x03 | S->C
public class SetCompression {

    private SetCompression() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int threshold = buffer.readVarInt();

        log.log(packetInfo, "Compression Threshold: {0}", threshold);
    }
    
}