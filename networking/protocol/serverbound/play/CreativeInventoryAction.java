package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;
import util.Slot;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

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