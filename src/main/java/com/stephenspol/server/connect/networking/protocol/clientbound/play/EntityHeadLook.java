package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x3A | S->C
public class EntityHeadLook {

    private EntityHeadLook() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        short yaw = buffer.readAngle();

        log.log(packetInfo, "Entity ID: {0}, Head Angle: {1}", new Object[]{entityID, yaw});
    }
    
}