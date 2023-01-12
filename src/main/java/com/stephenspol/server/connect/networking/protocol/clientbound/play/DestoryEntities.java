package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

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