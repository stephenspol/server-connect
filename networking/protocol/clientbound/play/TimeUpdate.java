package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x4E | S->C
public class TimeUpdate {

    private TimeUpdate() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        long worldAge = buffer.readLong();
        long timeOfDay = buffer.readLong();

        log.log(packetInfo, "World Age: {0}", worldAge);
        log.log(packetInfo, "Time of Day: {0}", timeOfDay);
    }
    
}