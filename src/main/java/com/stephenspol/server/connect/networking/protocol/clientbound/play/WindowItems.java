package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.Arrays;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

import com.stephenspol.server.connect.util.Slot;

// Packet ID 0x13 | S->C
public class WindowItems {

    private WindowItems() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        short windowID = buffer.readUnsignedByte();
        
        short length = buffer.readShort();

        Slot[] slots = new Slot[length];

        for (int i = 0; i < length; i++) {
            slots[i] = buffer.readSlot();
        }

        log.log(packetInfo, "Window ID: {0}, Slots: {1}", new Object[]{windowID, Arrays.toString(slots)});
    }
    
}