package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x1A | S->C
public class EntityStatus {

    private EntityStatus() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readInt();
        byte entityStatus = buffer.readByte();

        log.log(packetInfo, "Entity ID: {0}", entityID);
        log.log(packetInfo, "Entity Status: {0}", entityStatus);
    }
    
}
