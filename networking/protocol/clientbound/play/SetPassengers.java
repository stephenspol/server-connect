package networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.Arrays;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x4B | S->C
public class SetPassengers {

    private SetPassengers() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();

        int length = buffer.readVarInt();

        int[] passengers = new int[length];

        for (int i = 0; i < length; i++) {
            passengers[i] = buffer.readVarInt();
        }

        log.log(packetInfo, "Entity ID: {0}", entityID);
        log.log(packetInfo, "Passengers: {0}", Arrays.toString(passengers));
    }
    
}