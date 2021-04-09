package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x4A | S->C
public class ScoreboardObjective {

    private ScoreboardObjective() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String objectiveName = buffer.readString();

        byte mode = buffer.readByte();

        log.log(packetInfo, "Objective Name: {0}", objectiveName);

        if (mode != 1) {
            log.log(packetInfo, "{0}", (mode == 0) ? "Create Scoreboard" : "Update text");

            String objectiveValue = buffer.readString();
            int type = buffer.readVarInt();

            log.log(packetInfo, "Objective Value: {0}, Type: {1}", new Object[]{objectiveValue, ((type == 0) ? "Integer" : "Hearts")});

        } else {
            log.log(packetInfo, "Remove Scoreboard");
        }
    }
    
}