package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x19 Play / 0x00 Login | S->C
public class Disconnect {

    private Disconnect() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String reason = buffer.readString();

        log.log(packetInfo, "Disconnected, Reason: {0}", reason);
    }
    
}