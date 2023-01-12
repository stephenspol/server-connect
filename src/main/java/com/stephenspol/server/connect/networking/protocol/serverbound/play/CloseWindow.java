package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x0A | C->S
public class CloseWindow {

    private CloseWindow() {}

    public static byte[] execute(byte windowID) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x0A;

        buffer.writeByte(packetID);

        buffer.writeByte(windowID);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Window ID: {0}", windowID);

        return buffer.getPacket();
    }
    
}