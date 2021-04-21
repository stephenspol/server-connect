package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x36 | S->C
public class DestoryEntities {

    private DestoryEntities() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int length = buffer.readVarInt();

        int[] entityIDs = new int[length];

        for (int i = 0; i < length; i++) {
            entityIDs[i] = buffer.readVarInt();

            log.log(packetInfo, "Destory entity {0}", entityIDs[i]);
        }
    }
    
}