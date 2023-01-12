package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x2D | S->C
public class OpenWindow {

    private OpenWindow() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int windowID = buffer.readVarInt();
        int windowType = buffer.readVarInt();

        String windowTitle = buffer.readString();

        log.log(packetInfo, "Open window ID {0}, type {1} and title {2}", new Object[]{windowID, windowType, windowTitle});
    }
    
}