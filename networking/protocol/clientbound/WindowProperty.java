package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x14 | S->C
public class WindowProperty {

    private WindowProperty() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        short windowID = buffer.readUnsignedByte();

        short property = buffer.readShort();
        short value = buffer.readShort();

        log.log(packetInfo, "Window ID: {0}, Property: {1}, Value: {2}", new short[]{windowID, property, value});
    }
    
}