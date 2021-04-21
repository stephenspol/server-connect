package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x2C | S->C
public class OpenBook {

    private OpenBook() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int hand = buffer.readVarInt();

        log.log(packetInfo, "Open book on {0}", (hand == 0) ? "Main hand" : "Off hand");
    }
    
}