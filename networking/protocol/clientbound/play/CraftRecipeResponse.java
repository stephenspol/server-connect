package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x2F | S->C
public class CraftRecipeResponse {

    private CraftRecipeResponse() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte windowID = buffer.readByte();
        String recipe = buffer.readString();

        log.log(packetInfo, "Window ID {0} created recipe {1}", new Object[]{windowID, recipe});
    }
    
}