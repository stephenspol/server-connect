package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x0E | S->C
public class ChatMessage {

    private ChatMessage() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String json = buffer.readString();
        byte position = buffer.readByte();
        UUID sender = buffer.readUUID();

        log.log(packetInfo, "Data: {0}", json);
        log.log(packetInfo, "Position: {0}, Sender: {1}", new Object[]{position, sender});
    }
    
}