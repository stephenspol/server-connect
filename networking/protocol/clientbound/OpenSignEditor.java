package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x2E | S->C
public class OpenSignEditor {

    private OpenSignEditor() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int[] pos = buffer.readPos();

        log.log(packetInfo, "Edit sign at X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});
    }
    
}