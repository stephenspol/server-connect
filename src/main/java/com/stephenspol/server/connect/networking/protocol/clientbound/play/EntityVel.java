package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x46 | S->C
public class EntityVel {

    private EntityVel() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();

        short velX = buffer.readShort();
        short velY = buffer.readShort();
        short velZ = buffer.readShort();

        log.log(packetInfo, "Entity ID: {0}", entityID);
        log.log(packetInfo, "Velocity X: {0}, Y: {1}, Z: {2}", new Object[]{velX, velY, velZ});
    }
    
}