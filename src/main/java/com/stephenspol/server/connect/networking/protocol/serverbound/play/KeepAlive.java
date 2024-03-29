package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x10 | C->S
public class KeepAlive {

    private KeepAlive() {}

    public static byte[] execute(long keepAliveID) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x10;

        buffer.writeByte(packetID);

        buffer.writeLong(keepAliveID);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Keep Alive ID: {0}", keepAliveID);

        return buffer.getPacket();
    }
    
}