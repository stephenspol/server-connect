package networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x03 | S->C
public class SpawnPainting {

    private SpawnPainting() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        UUID uuid = buffer.readUUID();

        int motive = buffer.readVarInt();
        int[] pos = buffer.readPos();

        byte dir = buffer.readByte();

        log.log(packetInfo, "Painting EID: {0}, UUID: {1}", new Object[]{entityID, uuid});
        log.log(packetInfo, "Motive: {0}, Location X: {1}, Y: {2}, Z: {3}, Direction: {4}", new Object[]{motive, pos[0], pos[1], pos[2], dir});
    }
    
}