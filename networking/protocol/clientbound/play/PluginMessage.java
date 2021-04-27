package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x17 | S->C
public class PluginMessage {

    private PluginMessage() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String channel = buffer.readString();
            
        log.log(packetInfo, "Identifier: {0}", channel);
            
        String data = buffer.readString();
            
        log.log(packetInfo, "Data: {0}\n", data);
    }
    
}
