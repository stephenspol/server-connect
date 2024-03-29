package com.stephenspol.server.connect.networking.protocol.clientbound.login;

import java.io.IOException;
import java.util.UUID;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x02 | S->C
public class LoginSuccess {

    private LoginSuccess() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        UUID uuid = buffer.readUUID();

        String username = buffer.readString();

        log.log(packetInfo, "Player UUID: {0}, Username: {1}", new Object[]{uuid, username});
    }
    
}