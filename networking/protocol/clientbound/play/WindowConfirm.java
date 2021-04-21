package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x11 | S->C
public class WindowConfirm {

    private WindowConfirm() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte windowID = buffer.readByte();
        short action = buffer.readShort();

        boolean accepted = buffer.readBoolean();

        log.log(packetInfo, "Window ID: {0}, Action: {1}, Accepted: {2}", new Object[]{windowID, action, accepted});
    }
    
}