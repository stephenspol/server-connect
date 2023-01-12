package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x14 | S->C
public class WindowProperty {

    private WindowProperty() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        short windowID = buffer.readUnsignedByte();

        short property = buffer.readShort();
        short value = buffer.readShort();

        log.log(packetInfo, "Window ID: {0}, Property: {1}, Value: {2}", new Object[]{windowID, property, value});
    }
    
}