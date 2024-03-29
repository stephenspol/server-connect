package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x2E | S->C
public class OpenSignEditor {

    private OpenSignEditor() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int[] pos = buffer.readPos();

        log.log(packetInfo, "Edit sign at X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});
    }
    
}