package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x18 | C->S
public class PickItem {

    private PickItem() {}

    public static byte[] execute(int slotToUse) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x18;

        buffer.writeByte(packetID);

        buffer.writeVarInt(slotToUse);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Slot To Use: {0}", slotToUse);

        return buffer.getPacket();
    }
    
}