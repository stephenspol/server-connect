package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x1C | S->C
public class UnloadChunk {

    private UnloadChunk() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int x = buffer.readInt();
        int z = buffer.readInt();

        log.log(packetInfo, "Chunk located at X: {0}, Y: {1}, unloaded", new Object[]{x, z});
    }
    
}