package com.stephenspol.server.connect.networking.protocol.clientbound.login;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x03 | S->C
public class SetCompression {

    private SetCompression() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int threshold = buffer.readVarInt();

        log.log(packetInfo, "Compression Threshold: {0}", threshold);
    }
    
}