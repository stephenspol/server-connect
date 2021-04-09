package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x2A | S->C
public class EntityMovement {

    private EntityMovement() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();

        log.log(packetInfo, "Entity {0} has movement", entityID);
    }
    
}