package networking.protocol.clientbound;

import java.io.IOException;
import java.util.Arrays;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;
import util.Tag;

// Packet ID 0x5B | S->C
public class Tags {

    private Tags() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        Tag[] blockTags = readTags(buffer);
        Tag[] itemTags = readTags(buffer);
        Tag[] fluidTags = readTags(buffer);
        Tag[] entityTags = readTags(buffer);

        log.log(packetInfo, "Block Tags: {0}", Arrays.toString(blockTags));
        log.log(packetInfo, "Item Tags: {0}", Arrays.toString(itemTags));
        log.log(packetInfo, "Fluid Tags: {0}", Arrays.toString(fluidTags));
        log.log(packetInfo, "Entities Tags: {0}", Arrays.toString(entityTags));
    }
    
    private static Tag[] readTags(MinecraftInputBuffer buffer) throws IOException {
        int length = buffer.readVarInt();

        Tag[] tags = new Tag[length];

        for (int i = 0; i < length; i++) {
            String name = buffer.readString();

            int size = buffer.readVarInt();

            int[] entries = new int[size];

            for (int j = 0; j < size; j++) {
                entries[j] = buffer.readVarInt(); 
            }

            tags[i] = new Tag(name, entries);
        }

        return tags;
    }
}