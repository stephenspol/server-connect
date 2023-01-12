package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x04 | C->S
public class ClientStatus {

    private ClientStatus() {}

    public static byte[] execute(int actionID) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x04;

        buffer.writeByte(packetID);

        buffer.writeVarInt(actionID); // 0 - Respawn | 1 - Request Stats

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Set Difficulty: {0}", actionID);

        return buffer.getPacket();
    }
    
}