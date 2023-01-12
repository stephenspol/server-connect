package com.stephenspol.server.connect.networking.protocol.serverbound.login;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x00 | C->S
public class LoginStart {

    private LoginStart() {}

    public static byte[] execute(String name) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();
    
        byte packetID = 0x00;

        buffer.writeByte(packetID);

        buffer.writeString(name);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Player Username: {0}", name);

        return buffer.getPacket();
    }
    
}