package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x2C | S->C
public class OpenBook {

    private OpenBook() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int hand = buffer.readVarInt();

        log.log(packetInfo, "Open book on {0}", (hand == 0) ? "Main hand" : "Off hand");
    }
    
}