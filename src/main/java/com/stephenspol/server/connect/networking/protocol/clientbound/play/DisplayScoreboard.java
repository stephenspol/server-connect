package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x43 | S->C
public class DisplayScoreboard {

    private DisplayScoreboard() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte pos = buffer.readByte();
        String scoreName = buffer.readString();

        if (pos == 0) {
            log.log(packetInfo, "Postion of scoreboard: List");
        } else if (pos == 1) {
            log.log(packetInfo, "Postion of scoreboard: Sidebar");
        } else if (pos == 2) {
            log.log(packetInfo, "Postion of scoreboard: Below Name");
        } else {
            log.log(packetInfo, "Team color");
        }

        log.log(packetInfo, "Score Name: {0}", scoreName);
    }
    
}