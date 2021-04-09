package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x19 | S->C
public class Disconnect {

    private Disconnect() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String reason = buffer.readString();

        log.log(packetInfo, "Disconnected, Reason: {0}", reason);
    }
    
}