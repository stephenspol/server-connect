package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;
import com.stephenspol.server.connect.util.Slot;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x09 | C->S
public class ClickWindow {

    private ClickWindow() {}

    public static byte[] execute(byte windowID, short slotNum, byte button, short actionNum, int mode, Slot clickedItem) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x09;

        buffer.writeByte(packetID);

        buffer.writeByte(windowID);

        buffer.writeShort(slotNum);

        buffer.writeByte(button);

        buffer.writeShort(actionNum);

        buffer.writeVarInt(mode);

        buffer.writeSlot(clickedItem);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Window ID: {0}, Action Number: {1},  Slot Index: {2}", new Object[]{windowID, actionNum, slotNum});

        log.log(packetInfo, "Click Mode: {0}, Button Clicked: {1}, Clicked Item: {2}", new Object[]{mode, button, clickedItem});

        return buffer.getPacket();
    }
    
}