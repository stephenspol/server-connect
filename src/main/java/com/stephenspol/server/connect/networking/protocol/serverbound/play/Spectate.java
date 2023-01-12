package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;

import java.util.UUID;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x2D | C->S
public class Spectate {

    private Spectate() {}

    public static byte[] execute(UUID uuid) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x2D;

        buffer.writeByte(packetID);

        buffer.writeUUID(uuid);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Spectate: {0}", uuid);

        return buffer.getPacket();
    }
    
}