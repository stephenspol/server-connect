package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x1F | C->S
public class SetDisplayedRecipe {

    private SetDisplayedRecipe() {}

    public static byte[] execute(String recipe) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x1F;

        buffer.writeByte(packetID);

        buffer.writeString(recipe);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Recipe: {0}", recipe);

        return buffer.getPacket();
    }
    
}