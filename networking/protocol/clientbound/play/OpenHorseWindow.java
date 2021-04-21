package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x1E | S->C
public class OpenHorseWindow {

    private OpenHorseWindow() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte windowID = buffer.readByte();
        int numOfSlots = buffer.readVarInt();

        int entityID = buffer.readInt();

        log.log(packetInfo, "Window ID opened: {0}", windowID);
        log.log(packetInfo, "Number of Slots: {0}", numOfSlots);
        log.log(packetInfo, "Entity ID: {0}", entityID);
    }
    
}