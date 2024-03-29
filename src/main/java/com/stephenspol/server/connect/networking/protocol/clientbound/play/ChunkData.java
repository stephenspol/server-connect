package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.nbt.Tag;
import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x20 | S->C
public class ChunkData {

    private ChunkData() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int chunkX = buffer.readInt();
        int chunkZ = buffer.readInt();

        boolean fullChunk = buffer.readBoolean();

        log.log(packetInfo, "Chunk X: {0}, Chunk Z: {1}, Is Full Chunk: {2}", new Object[]{chunkX, chunkZ, fullChunk});

        int primaryBitMask = buffer.readVarInt();
        Tag<?> heightMaps = buffer.readNBT();

        log.log(packetInfo, "Primary Bit Mask: {0}, HeightMaps: {1}", new Object[]{primaryBitMask, heightMaps});

        if (fullChunk) {
            int length = buffer.readVarInt();

            int[] boimes = new int[length];

            for (int i = 0; i < length; i++) {
                boimes[i] = buffer.readVarInt();
                // It goes X, Z, Y
                if (i % 3 == 0 && i != 0) {
                    //log.log(packetInfo, "Boime {0}, X: {1}, Y:{2}, Z:{3}", new Object[]{i, boimes[i-2], boimes[i], boimes[i-1]});
                }
            }
        }

        int length = buffer.readVarInt();

        byte[] data = buffer.readBytes(length);

        log.log(packetInfo, "Data: {0}", data);

        length = buffer.readVarInt();

        Tag<?>[] blocks = new Tag<?>[length];

        for (int i = 0; i < length; i++) {
            blocks[i] = buffer.readNBT();

            log.log(packetInfo, "NBT Data Block {0}: {1}", new Object[]{i, blocks[i]});
        }

    }
    
}