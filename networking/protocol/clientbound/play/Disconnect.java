package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x19 Play / 0x00 Login | S->C
public class Disconnect {

    private Disconnect() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String reason = buffer.readString();

        log.log(packetInfo, "Disconnected, Reason: {0}", reason);
    }
    
}