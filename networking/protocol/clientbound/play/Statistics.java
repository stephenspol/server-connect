package networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.Arrays;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;
import util.Statistic;

// Packet ID 0x06 | S->C
public class Statistics {

    private Statistics() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int length = buffer.readVarInt();

        Statistic[] statistics = new Statistic[length];

        for (int i = 0; i < length; i++) {
            int categoryID = buffer.readVarInt();
            int statisticID = buffer.readVarInt();

            int value = buffer.readVarInt();

            statistics[i] = new Statistic(categoryID, statisticID, value);
        }

        log.log(packetInfo, "Statistics: {0}", Arrays.toString(statistics));
    }
    
}