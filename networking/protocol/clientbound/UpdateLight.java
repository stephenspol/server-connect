package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

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

        log.log(packetInfo, "Sky Light Mask: {0}, Block Light Mask: {1}", new int[]{skyLightMask, blockLightMask});
        log.log(packetInfo, "Empty Sky Light Mask: {0}, Empty Sky Block Light Mask: {1}", new int[]{emptySkyLightMask, emptyBlockLightMask});

        log.log(packetInfo, "Sky Lights: {0}", skyLights);
        log.log(packetInfo, "Block Lights: {0}", blockLights);

    }
    
}