package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x48 | S->C
public class SetXP {

    private SetXP() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        float xpBar = buffer.readFloat();
        int lvl = buffer.readVarInt();
        int totalXP = buffer.readVarInt();

        log.log(packetInfo, "Experience Bar: {0}", xpBar);
        log.log(packetInfo, "Level: {0}, Total XP: {1}", new Object[]{lvl, totalXP});
    }
    
}