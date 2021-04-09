package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x3B | S->C
public class MultiBlockChange {

    private MultiBlockChange() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        long chunkSection = buffer.readLong();

        int sectionX = (int) (chunkSection >> 42);
        int sectionY = (int) (chunkSection << 44 >> 44);
        int sectionZ = (int) (chunkSection << 22 >> 42);

        boolean inverseTrustEdges = buffer.readBoolean();

        log.log(packetInfo, "Chunk X: {0}, Y: {1}, Z: {2}, Inverse Trust Edges: {3}", new Object[]{sectionX, sectionY, sectionZ, inverseTrustEdges});

        int length = buffer.readVarInt();

        short[][] blocks = new short[length][4];

        for (int i = 0; i < length; i++) {
            long blockPosition = buffer.readLong();

            short id = (short) (blockPosition << 12);
            short x = (short) (blockPosition << 8);
            short z = (short) (blockPosition << 4);
            short y = (short) (blockPosition);

            blocks[i][0] = id;
            blocks[i][1] = x;
            blocks[i][2] = z;
            blocks[i][3] = y;

            log.log(packetInfo, "Block {0}: ID: {1}, X: {2}, Y: {4}, Z: {3}", blocks[i]);
        }
    }
    
}