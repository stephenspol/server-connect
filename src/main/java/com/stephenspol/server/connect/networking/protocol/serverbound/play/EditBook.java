package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;
import com.stephenspol.server.connect.util.Slot;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x0C | C->S
public class EditBook {

    private EditBook() {}

    public static byte[] execute(Slot newBook, boolean isSigned, int hand) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x0C;

        buffer.writeByte(packetID);

        buffer.writeSlot(newBook);

        buffer.writeBoolean(isSigned);

        buffer.writeVarInt(hand);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "New Book: {0}", newBook);

        log.log(packetInfo, "Is Signed: {0}, Hand: {1}", new Object[]{isSigned, hand});

        return buffer.getPacket();
    }
    
}