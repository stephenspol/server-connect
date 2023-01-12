package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x07 | S->C
public class AckPlayerDigging {

    private AckPlayerDigging() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int[] location = buffer.readPos();
        int blockID = buffer.readVarInt();

        int status = buffer.readVarInt();
        boolean successful = buffer.readBoolean();

        log.log(packetInfo, "Block ID {0} at location X: {1}. Y: {2}, Z: {3}", new Object[]{blockID, location[0], location[1], location[2]});
        log.log(packetInfo, "Status: {0}, Was successful: {1}", new Object[]{status, successful});
    }
    
}