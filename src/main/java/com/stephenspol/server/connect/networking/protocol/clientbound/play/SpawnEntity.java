package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x00 | S->C
public class SpawnEntity {

    private SpawnEntity() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        UUID uuid = buffer.readUUID();
        int type = buffer.readVarInt();

        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        short pitch = buffer.readAngle();
        short yaw = buffer.readAngle();

        int data = buffer.readInt();

        short velX = buffer.readShort();
        short velY = buffer.readShort();
        short velZ = buffer.readShort();

        log.log(packetInfo, "Spawn entity ID {0}, UUID: {1}, type: {2}", new Object[]{entityID, uuid, type});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}, Pitch: {3}, Yaw: {4}", new Object[]{x, y, z, pitch, yaw});

        log.log(packetInfo, "Data: {0}, Velocities: [X: {1}, Y: {2}, Z: {3}]", new Object[]{data, velX, velY, velZ});
    }
    
}