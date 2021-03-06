package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x45 | S->C
public class AttachEntity {

    private AttachEntity() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int attachedEntityID = buffer.readVarInt();
        int holdingEntityID = buffer.readVarInt();

        log.log(packetInfo, "Entity {0} is attached to {1}", new Object[]{attachedEntityID, holdingEntityID});
    }
    
}