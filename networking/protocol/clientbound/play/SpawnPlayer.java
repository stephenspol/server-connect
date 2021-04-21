package networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x04 | S->C
public class SpawnPlayer {

    private SpawnPlayer() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        UUID uuid = buffer.readUUID();

        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        short yaw = buffer.readAngle();
        short pitch = buffer.readAngle();

        log.log(packetInfo, "Spawn entity id: {0}, UUID: {1}", new Object[]{entityID, uuid});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}, Yaw: {3}, Pitch: {4}", new Object[]{x, y, z, yaw, pitch});
    }
    
}