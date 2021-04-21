package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x0B | S->C
public class BlockChange {

    private BlockChange() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int[] location = buffer.readPos();
        int blockID = buffer.readVarInt();

        log.log(packetInfo, "Change block id {0} at location X: {1}, Y: {2}. Z: {3}", new Object[]{blockID, location[0], location[1], location[2]});
    }
    
}