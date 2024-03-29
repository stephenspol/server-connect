package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x02 | C->S
public class SetDifficulty {

    private SetDifficulty() {}

    public static byte[] execute(byte difficulty) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x02;

        buffer.writeByte(packetID);

        buffer.writeByte(difficulty);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Set Difficulty: {0}", difficulty);

        return buffer.getPacket();
    }
    
}