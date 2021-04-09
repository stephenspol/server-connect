package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x4D | S->C
public class UpdateScore {

    private UpdateScore() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String entityName = buffer.readString();

        byte action = buffer.readByte();

        String objectiveName = buffer.readString();

        log.log(packetInfo, "Entity Name: {0}", entityName);
        log.log(packetInfo, "Action: {0}", (action == 0)? "Create/Update" : "Remove");

        log.log(packetInfo, "Objective Name: {0}", objectiveName);

        if (action != 1) {
            int value = buffer.readVarInt();
            log.log(packetInfo, "Value: {0}", value);
        }
    }
    
}