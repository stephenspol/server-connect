package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x0B | C->S
public class PluginMessage {

    private PluginMessage() {}

    public static byte[] execute(String channel, byte[] data) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x0B;

        buffer.writeByte(packetID);

        buffer.writeString(channel);

        buffer.writeBytes(data);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Channel: {0}, Data: {1}", new Object[]{channel, data});

        return buffer.getPacket();
    }
    
}