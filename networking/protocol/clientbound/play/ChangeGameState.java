package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x1D | S->C
public class ChangeGameState {

    private ChangeGameState() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        short reason = buffer.readUnsignedByte();
        float value = buffer.readFloat();

        log.log(packetInfo, "Change game state for reason {0}, Value: {1}", new Object[]{reason, value});
    }
    
}