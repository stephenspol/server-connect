package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x31 | S->C
public class CombatEvent {

    private CombatEvent() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int event = buffer.readVarInt();

        if (event == 0) {
            log.log(packetInfo, "Enter Combat");
        } else if (event == 1) {
            int duration = buffer.readVarInt();
            int entityID = buffer.readInt();

            log.log(packetInfo, "End Combat with entity {0}, combat lasted {1} ticks", new Object[]{duration, entityID});
        } else if (event == 2) {
            int playerID = buffer.readVarInt();
            int entityID = buffer.readInt();
            String message = buffer.readString();

            log.log(packetInfo, "Entity {1} killed Player {0}", new Object[]{playerID, entityID});
            log.log(packetInfo, "Death Message: {0}", message);
        } else {
            throw new IOException("Event " + event + " is invalid!");
        }
    }
    
}