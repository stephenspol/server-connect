package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x55 | S->C
public class CollectItem {

    private CollectItem() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int collectedEntityID = buffer.readVarInt();
        int collectorEntityID = buffer.readVarInt();

        int pickupCount = buffer.readVarInt();

        log.log(packetInfo, "Collected Entity ID: {0}", collectedEntityID);
        log.log(packetInfo, "Collector Entity ID: {0}", collectorEntityID);

        log.log(packetInfo, "Item Pickup Count: {0}", pickupCount);
    }
    
}