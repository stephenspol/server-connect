package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x41 | S->C
public class UpdateViewDist {

    private UpdateViewDist() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int viewDist = buffer.readVarInt();

        log.log(packetInfo, "Update View Distance: {0}", viewDist);
    }
    
}