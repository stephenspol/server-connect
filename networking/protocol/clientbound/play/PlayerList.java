package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x53 | S->C
public class PlayerList {

    private PlayerList() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String header = buffer.readString();

        String footer = buffer.readString();

        log.log(packetInfo, "Header: {0}", header);
        log.log(packetInfo, "Footer: {0}", footer);
    }
    
}