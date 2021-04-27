package networking.protocol.clientbound.status;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x01 | S->C
public class Pong {

    private Pong() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        long pingtime = buffer.readLong();

        log.log(packetInfo, "Pong Time: {0}", pingtime);
    }
    
}