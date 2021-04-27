package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x42 | S->C
public class SpawnPosition {

    private SpawnPosition() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException {
        int[] pos = buffer.readPos();
        
        log.fine("Spawn Position\n");

        log.log(packetInfo, "X: {0}", pos[0]);
        log.log(packetInfo, "Y: {0}", pos[1]);
        log.log(packetInfo, "Z: {0}\n", pos[2]);
    }
    
}
