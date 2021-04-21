package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x10 | S->C [WIP]
public class DeclareCommands {

    private DeclareCommands() {}
    
    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int length = buffer.readVarInt();

        log.log(packetInfo, "Number of nodes: {0}", length);

        log.log(packetInfo, "Packet is incomplete");
    }
    
}