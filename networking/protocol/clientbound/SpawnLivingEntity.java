package networking.protocol.clientbound;

import java.io.IOException;
import java.util.UUID;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x02 | S->C
public class SpawnLivingEntity {

    private SpawnLivingEntity() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException {
        int entityID = buffer.readVarInt();
        UUID uuid = buffer.readUUID();
        int type = buffer.readVarInt();

        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        short yaw = buffer.readAngle();
        short pitch = buffer.readAngle();
        short headPitch = buffer.readAngle();

        short velX = buffer.readShort();
        short velY = buffer.readShort();
        short velZ = buffer.readShort();

        log.log(packetInfo, "Spawn entity ID {0}, UUID: {1}, Type: {2}", new Object[]{entityID, uuid, type});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}, Yaw: {3}, Pitch: {4}, Head Pitch: {5}", new Object[]{x, y, z, yaw, pitch, headPitch});

        log.log(packetInfo, "Velocities: [X: {0}, Y: {1}, Z: {2}]", new short[]{velX, velY, velZ});
    }
    
}