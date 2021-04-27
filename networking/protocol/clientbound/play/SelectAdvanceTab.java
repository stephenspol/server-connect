package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x3C | S->C
public class SelectAdvanceTab {

    private SelectAdvanceTab() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        boolean hasID = buffer.readBoolean();

        if (hasID) {
            String identifier = buffer.readString();

            log.log(packetInfo, "Select Advancement Tab: {0}", identifier);
        } else {
            log.log(packetInfo, "Switch to first tab");
        }
    }
    
}