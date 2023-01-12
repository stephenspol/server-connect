package com.stephenspol.server.connect.networking.protocol.serverbound.login;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import java.util.Arrays;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x02 | C->S
public class LoginPluginResponse {

    private LoginPluginResponse() {}

    public static byte[] execute(int messageID, byte[] data) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();
    
        byte packetID = 0x02;

        buffer.writeByte(packetID);

        buffer.writeVarInt(messageID);

        if (data != null) {
            buffer.writeBoolean(true);

            buffer.writeBytes(data);
        } else {
            buffer.writeBoolean(false);
        }

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Message ID: {0}", messageID);
        log.log(packetInfo, "Data: {0}", (data != null) ? Arrays.toString(data) : "No Data");

        return buffer.getPacket();
    }
    
}