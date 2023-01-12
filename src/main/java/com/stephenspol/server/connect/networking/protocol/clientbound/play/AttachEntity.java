package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x45 | S->C
public class AttachEntity {

    private AttachEntity() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int attachedEntityID = buffer.readVarInt();
        int holdingEntityID = buffer.readVarInt();

        log.log(packetInfo, "Entity {0} is attached to {1}", new Object[]{attachedEntityID, holdingEntityID});
    }
    
}