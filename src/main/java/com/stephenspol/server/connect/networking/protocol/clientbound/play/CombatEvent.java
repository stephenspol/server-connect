package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

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