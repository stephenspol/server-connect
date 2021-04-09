package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x12 | S->C
public class CloseWindow {

    private CloseWindow() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        short windowID = buffer.readUnsignedByte();

        log.log(packetInfo, "Close window ID: {0}", windowID);
    }
    
}