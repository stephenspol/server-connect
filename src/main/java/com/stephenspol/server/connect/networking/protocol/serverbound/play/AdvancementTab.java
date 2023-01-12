package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x22 | C->S
public class AdvancementTab {

    private AdvancementTab() {}

    public static byte[] execute(int action, String tabID) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x22;

        buffer.writeByte(packetID);

        buffer.writeVarInt(action);

        if (action == 0) {
            buffer.writeString(tabID);
        }

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Action: {0}", action);

        if (action == 0) log.log(packetInfo, "Tab ID: {0}", tabID);

        return buffer.getPacket();
    }
    
}