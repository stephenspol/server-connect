package networking.protocol.clientbound;

import java.io.IOException;
import java.util.Arrays;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;
import util.Slot;

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