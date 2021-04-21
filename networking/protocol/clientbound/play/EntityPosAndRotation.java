package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x28 | S->C
public class EntityPosAndRotation {

    private EntityPosAndRotation() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
       int entityID = buffer.readVarInt();

       short x = buffer.readShort();
       short y = buffer.readShort();
       short z = buffer.readShort();

       short yaw = buffer.readAngle();
       short pitch = buffer.readAngle();

       boolean onGround = buffer.readBoolean();

       log.log(packetInfo, "Entity {0} delta X: {1}, Y: {2}, Z: {3}", new Object[]{entityID, x, y, z});

       log.log(packetInfo, "Yaw: {0}, Pitch: {1}", new Object[]{yaw, pitch});

       log.log(packetInfo, "On Ground: {0}", onGround);
    }
    
}