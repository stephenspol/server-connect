package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;
import util.Slot;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

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