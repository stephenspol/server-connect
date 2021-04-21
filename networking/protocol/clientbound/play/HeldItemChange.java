package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x3F | S->C
public class HeldItemChange {

    private HeldItemChange() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte slot = buffer.readByte();

        log.log(packetInfo, "Player selected slot {0}\n", slot);
    }
    
}