package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x49 | S->C
public class UpdateHealth {

    private UpdateHealth() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        float health = buffer.readFloat();

        int food = buffer.readVarInt();
        float foodSaturation = buffer.readFloat();

        log.log(packetInfo, "Health: {0}", health);

        log.log(packetInfo, "Food: {0}, Food Saturation: {1}", new Object[]{food, foodSaturation});
    }
    
}