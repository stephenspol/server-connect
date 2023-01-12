package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;

import com.stephenspol.server.connect.networking.Server;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x1A | C->S
public class PlayerAbilities {

    private PlayerAbilities() {}

    public static byte[] execute(byte flags) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x1A;

        buffer.writeByte(packetID);

        buffer.writeByte(flags);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Is Flying: {0}", (boolean) Server.getBit(flags, 1));

        return buffer.getPacket();
    }
    
}