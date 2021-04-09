package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x3A | S->C
public class EntityHeadLook {

    private EntityHeadLook() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        short yaw = buffer.readAngle();

        log.log(packetInfo, "Entity ID: {0}, Head Angle: {1}", new Object[]{entityID, yaw});
    }
    
}