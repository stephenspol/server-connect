package com.stephenspol.server.connect.networking.protocol.serverbound.login;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import java.util.Arrays;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x01 | C->S
public class EncryptionResponse {

    private EncryptionResponse() {}

    public static byte[] execute(byte[] sharedSecret, byte[] verifyToken) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();
    
        byte packetID = 0x01;

        buffer.writeByte(packetID);

        buffer.writeVarInt(sharedSecret.length);
        buffer.writeBytes(sharedSecret);

        buffer.writeVarInt(verifyToken.length);
        buffer.writeBytes(verifyToken);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Shared Secret: {0}", Arrays.toString(sharedSecret));
        log.log(packetInfo, "Verify Token: {0}", Arrays.toString(verifyToken));

        return buffer.getPacket();
    }
    
}