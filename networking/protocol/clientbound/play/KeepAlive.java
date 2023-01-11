package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;
import networking.protocol.ServerboundManager;
import networking.protocol.ServerboundPacket;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x1F | S->C
public class KeepAlive {

    private KeepAlive() {}

    public static void execute(MinecraftInputBuffer buffer, ServerboundManager serverboundManager) throws IOException{
        long keepAliveID = buffer.readLong();

        log.log(packetInfo, "Keep Alive Packet ID {0}", keepAliveID);

        serverboundManager.execute(ServerboundPacket.Play.KEEP_ALIVE.getId(), keepAliveID);
    }
    
}