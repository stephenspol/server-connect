package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x00 | C->S
public class TeleportConfirm {

    private TeleportConfirm() {}

    public static byte[] execute(int teleportID) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x00;

        buffer.writeByte(packetID);

        buffer.writeVarInt(teleportID);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Telport confirm: {0}", teleportID);

        return buffer.getPacket();
    }
    
}