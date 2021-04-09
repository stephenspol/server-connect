package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x38 | S->C
public class ResourcePackSend {

    private ResourcePackSend() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String url = buffer.readString();
        String hash = buffer.readString();

        log.log(packetInfo, "Resource Pack URL: {0}", url);
        log.log(packetInfo, "Hash: {0}", hash);
    }
    
}