package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x01 | S->C
public class SpawnXPOrb {

    private SpawnXPOrb() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();

        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        short count = buffer.readShort();

        log.log(packetInfo, "Spawn XP Orb {0} at X: {1}, Y: {2}, Z: {3}, Count: {4}", new Object[]{entityID, x, y, z, count});
    }
    
}