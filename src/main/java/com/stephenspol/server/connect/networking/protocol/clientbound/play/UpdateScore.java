package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x4D | S->C
public class UpdateScore {

    private UpdateScore() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String entityName = buffer.readString();

        byte action = buffer.readByte();

        String objectiveName = buffer.readString();

        log.log(packetInfo, "Entity Name: {0}", entityName);
        log.log(packetInfo, "Action: {0}", (action == 0)? "Create/Update" : "Remove");

        log.log(packetInfo, "Objective Name: {0}", objectiveName);

        if (action != 1) {
            int value = buffer.readVarInt();
            log.log(packetInfo, "Value: {0}", value);
        }
    }
    
}