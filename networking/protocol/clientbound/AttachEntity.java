package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x45 | S->C
public class AttachEntity {

    private AttachEntity() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int attachedEntityID = buffer.readVarInt();
        int holdingEntityID = buffer.readVarInt();

        log.log(packetInfo, "Entity {0} is attached to {1}", new int[]{attachedEntityID, holdingEntityID});
    }
    
}