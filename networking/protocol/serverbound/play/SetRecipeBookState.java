package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x1E | C->S
public class SetRecipeBookState {

    private SetRecipeBookState() {}

    public static byte[] execute(int bookID, boolean bookOpen, boolean filterActive) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x1E;

        buffer.writeByte(packetID);

        buffer.writeVarInt(bookID);

        buffer.writeBoolean(bookOpen);
        buffer.writeBoolean(filterActive);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Book ID: {0}, Book Open: {1}, Filter Active: {2}", new Object[]{bookID, bookOpen, filterActive});

        return buffer.getPacket();
    }
    
}