package networking.protocol.clientbound.status;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x00 | S->C
public class Response {

    private Response() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String json = buffer.readString();

        log.log(packetInfo, "Server response: {0}", json);
    }
    
}