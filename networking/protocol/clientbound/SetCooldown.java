package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x16 | S->C
public class SetCooldown {

    private SetCooldown() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int itemID = buffer.readVarInt();
        int cooldown = buffer.readVarInt();

        log.log(packetInfo, "Item ID {0} has cooldown of {1} ticks", new Object[]{itemID, cooldown});
    }
    
}