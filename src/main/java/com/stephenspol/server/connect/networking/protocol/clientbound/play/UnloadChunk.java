package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x1C | S->C
public class UnloadChunk {

    private UnloadChunk() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int x = buffer.readInt();
        int z = buffer.readInt();

        log.log(packetInfo, "Chunk located at X: {0}, Y: {1}, unloaded", new Object[]{x, z});
    }
    
}