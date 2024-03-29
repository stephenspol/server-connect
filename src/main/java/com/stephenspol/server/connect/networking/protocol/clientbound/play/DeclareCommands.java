package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x10 | S->C [WIP]
public class DeclareCommands {

    private DeclareCommands() {}
    
    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int length = buffer.readVarInt();

        log.log(packetInfo, "Number of nodes: {0}", length);

        log.log(packetInfo, "Packet is incomplete");
    }
    
}