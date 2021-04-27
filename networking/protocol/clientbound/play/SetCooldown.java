package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x16 | S->C
public class SetCooldown {

    private SetCooldown() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int itemID = buffer.readVarInt();
        int cooldown = buffer.readVarInt();

        log.log(packetInfo, "Item ID {0} has cooldown of {1} ticks", new Object[]{itemID, cooldown});
    }
    
}