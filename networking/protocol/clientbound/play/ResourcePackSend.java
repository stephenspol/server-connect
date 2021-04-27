package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

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