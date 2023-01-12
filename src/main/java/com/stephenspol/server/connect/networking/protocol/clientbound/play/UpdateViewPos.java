package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x40 | S->C
public class UpdateViewPos {

    private UpdateViewPos() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int chunkX = buffer.readVarInt();
        int chunkZ = buffer.readVarInt();

        log.log(packetInfo, "Update Chunk X: {0}", chunkX);
        log.log(packetInfo, "Update Chunk Z: {0}", chunkZ);
    }
    
}
