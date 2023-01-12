package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;
import com.stephenspol.server.connect.util.Slot;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x28 | C->S
public class CreativeInventoryAction {

    private CreativeInventoryAction() {}

    public static byte[] execute(short slot, Slot clickedSlot) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x28;

        buffer.writeByte(packetID);

        buffer.writeShort(slot);

        buffer.writeSlot(clickedSlot);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Slot: {0}, Clicked Slot: {1}", new Object[]{slot, clickedSlot});

        return buffer.getPacket();
    }
    
}