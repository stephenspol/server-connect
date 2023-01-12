package com.stephenspol.server.connect.networking.protocol.serverbound.status;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x00 | C->S
public class Request {

    private Request() {}

    public static byte[] execute() {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();
    
        byte packetID = 0x00;

        buffer.writeByte(packetID);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        return buffer.getPacket();
    }
    
}