package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x24 | C->S
public class SetBeaconEffect {

    private SetBeaconEffect() {}

    public static byte[] execute(int primaryEffect, int secondaryEffect) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x24;

        buffer.writeByte(packetID);

        buffer.writeVarInt(primaryEffect);
        buffer.writeVarInt(secondaryEffect);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Primary Effect: {0}, Secondary Effect: {1}", new Object[]{primaryEffect, secondaryEffect});

        return buffer.getPacket();
    }
    
}