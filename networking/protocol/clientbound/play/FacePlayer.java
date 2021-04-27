package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x33 | S->C
public class FacePlayer {

    private FacePlayer() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int position = buffer.readVarInt();

        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        boolean isEntity = buffer.readBoolean();

        log.log(packetInfo, "Position: {0}", (position == 1) ? "Head" : "Feet");

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}", new Object[]{x, y, z});

        if (isEntity) {
            int entityID = buffer.readVarInt();
            int entityPosition = buffer.readVarInt();

            log.log(packetInfo, "Entity: {0}, Entity Positon: {1}", new Object[]{entityID, (entityPosition == 1) ? "Head" : "Feet"});
        }
    }
    
}