package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x29 | S->C
public class EntityRotation {

    private EntityRotation() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
       int entityID = buffer.readVarInt();

       short yaw = buffer.readAngle();
       short pitch = buffer.readAngle();

       boolean onGround = buffer.readBoolean();

       log.log(packetInfo, "Entity {0} Yaw: {1}, Pitch: {2}", new Object[]{entityID, yaw, pitch});

       log.log(packetInfo, "On Ground: {0}", onGround);
    }
    
}