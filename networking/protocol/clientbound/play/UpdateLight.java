package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x23 | S->C
public class UpdateLight {

    private UpdateLight() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int chunkX = buffer.readVarInt();
        int chunkZ = buffer.readVarInt();

        boolean trustEdges = buffer.readBoolean();

        int skyLightMask = buffer.readVarInt();
        int blockLightMask = buffer.readVarInt();

        int emptySkyLightMask = buffer.readVarInt();
        int emptyBlockLightMask = buffer.readVarInt();

        int length = buffer.readVarInt();

        byte[] skyLights = buffer.readBytes(length);

        length = buffer.readVarInt();

        byte[] blockLights = buffer.readBytes(length);

        log.log(packetInfo, "Update Lights at Chunk X: {0}, Chunk Y: {1}, Trust Edges: {2}", new Object[]{chunkX, chunkZ, trustEdges});

        log.log(packetInfo, "Sky Light Mask: {0}, Block Light Mask: {1}", new Object[]{skyLightMask, blockLightMask});
        log.log(packetInfo, "Empty Sky Light Mask: {0}, Empty Sky Block Light Mask: {1}", new Object[]{emptySkyLightMask, emptyBlockLightMask});

        log.log(packetInfo, "Sky Lights: Length {0}", skyLights.length);
        log.log(packetInfo, "Block Lights: Length {0}", blockLights.length);

    }
    
}