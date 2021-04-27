package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

import util.Slot;

// Packet ID 0x15 | S->C
public class SetSlot {

    private SetSlot() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte windowID = buffer.readByte();
        short slot = buffer.readShort();
        Slot slotData = buffer.readSlot();

        log.log(packetInfo, "Window ID: {0}, Slot {1} amd data {2}", new Object[]{windowID, slot, slotData});
    }
    
}