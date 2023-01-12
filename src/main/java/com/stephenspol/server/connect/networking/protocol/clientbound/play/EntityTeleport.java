package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x56 | S->C
public class EntityTeleport {

    private EntityTeleport() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();

        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        short yaw = buffer.readAngle();
        short pitch = buffer.readAngle();

        boolean onGround = buffer.readBoolean();

        log.log(packetInfo, "Entity ID: {0}", entityID);

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}", new Object[]{x, y, z});

        log.log(packetInfo, "Yaw: {0}, Pitch: {1}", new Object[]{yaw, pitch});

        log.log(packetInfo, "Is on Ground: {0}", onGround);
    }
    
}