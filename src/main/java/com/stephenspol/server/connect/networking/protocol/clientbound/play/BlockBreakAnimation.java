package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x08 | S->C
public class BlockBreakAnimation {

    private BlockBreakAnimation() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        int[] location = buffer.readPos();
        byte destoryStage = buffer.readByte();

        log.log(packetInfo, "Entity ID {0} located at X: {1}, Y: {2}, Z: {3}", new Object[]{entityID, location[0], location[1], location[2]});
        log.log(packetInfo, "Destory Stage: {0}", destoryStage);
    }
    
}