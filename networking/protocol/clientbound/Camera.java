package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x3E | S->C
public class Camera {

    private Camera() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int cameraID = buffer.readVarInt();

        log.log(packetInfo, "Camera Entity ID: {0}", cameraID);
    }
    
}