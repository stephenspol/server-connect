package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x06 | C->S
public class TabComplete {

    private TabComplete() {}

    public static byte[] execute(int transID, String text) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x06;

        buffer.writeByte(packetID);

        buffer.writeVarInt(transID);

        buffer.writeString(text);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Transaction ID: {0}", transID);
        log.log(packetInfo, "Text: {0}", text);

        return buffer.getPacket();
    }
    
}