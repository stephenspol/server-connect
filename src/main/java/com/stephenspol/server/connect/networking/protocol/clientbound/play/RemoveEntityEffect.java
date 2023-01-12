package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x37 | S->C
public class RemoveEntityEffect {

    private RemoveEntityEffect() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        byte effectID = buffer.readByte();

        log.log(packetInfo, "Entity {0}, remove {1} effect", new Object[]{entityID, effectID});
    }
    
}