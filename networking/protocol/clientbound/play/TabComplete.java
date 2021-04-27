package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x06 | S->C
public class TabComplete {

    private TabComplete() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int transactionID = buffer.readVarInt();

        String text = buffer.readString();

        log.log(packetInfo, "Tab Complete Transaction ID {0}, Text: {1}", new Object[]{transactionID, text});
    }
    
}