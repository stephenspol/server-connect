package com.stephenspol.server.connect.networking.protocol.clientbound.login;

import java.io.IOException;
import java.util.Arrays;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x04 | S->C
public class LoginPluginRequest {

    private LoginPluginRequest() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int messageID = buffer.readVarInt();

        String channel = buffer.readString();

        byte[] data = buffer.readFully();

        log.log(packetInfo, "Message ID: {0}, Channel: {1}", new Object[]{messageID, channel});
        log.log(packetInfo, "Data: {0}", Arrays.toString(data));
    }
    
}