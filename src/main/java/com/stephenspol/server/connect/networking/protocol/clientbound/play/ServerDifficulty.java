package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x0D | S->C
public class ServerDifficulty {

    private ServerDifficulty() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException {
        int difficulty = buffer.readUnsignedByte();
            
        log.log(packetInfo, "Server Difficulty: {0}", difficulty);

        boolean locked = buffer.readBoolean();

        log.log(packetInfo, "Difficulty Locked: {0}\n", locked); 
    }
    
}
