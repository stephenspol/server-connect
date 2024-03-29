package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x2C | C->S
public class Animation {

    private Animation() {}

    public static byte[] execute(int hand) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x2C;

        buffer.writeByte(packetID);

        buffer.writeVarInt(hand);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Hand: {0}", (hand == 0) ? "Main Hand" : "Off Hand");

        return buffer.getPacket();
    }
    
}