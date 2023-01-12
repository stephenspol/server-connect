package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x19 | C->S
public class CraftRecipeRequest {

    private CraftRecipeRequest() {}

    public static byte[] execute(byte windowID, String recipe, boolean makeAll) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x19;

        buffer.writeByte(packetID);

        buffer.writeByte(windowID);

        buffer.writeString(recipe);

        buffer.writeBoolean(makeAll);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Window ID: {0}, Recipe: {1}, Make All: {2}", new Object[]{windowID, recipe, makeAll});

        return buffer.getPacket();
    }
    
}