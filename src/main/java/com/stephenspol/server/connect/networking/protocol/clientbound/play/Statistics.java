package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.Arrays;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

import com.stephenspol.server.connect.util.Statistic;

// Packet ID 0x06 | S->C
public class Statistics {

    private Statistics() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int length = buffer.readVarInt();

        Statistic[] statistics = new Statistic[length];

        for (int i = 0; i < length; i++) {
            int categoryID = buffer.readVarInt();
            int statisticID = buffer.readVarInt();

            int value = buffer.readVarInt();

            statistics[i] = new Statistic(categoryID, statisticID, value);
        }

        log.log(packetInfo, "Statistics: {0}", Arrays.toString(statistics));
    }
    
}