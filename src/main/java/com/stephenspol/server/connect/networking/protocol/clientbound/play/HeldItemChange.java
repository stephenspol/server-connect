package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x3F | S->C
public class HeldItemChange {

    private HeldItemChange() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte slot = buffer.readByte();

        log.log(packetInfo, "Player selected slot {0}\n", slot);
    }
    
}