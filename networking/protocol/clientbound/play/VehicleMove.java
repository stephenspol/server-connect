package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x2B | S->C
public class VehicleMove {

    private VehicleMove() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        float yaw = buffer.readFloat();
        float pitch = buffer.readFloat();

        log.log(packetInfo, "Move vehicle X: {0}, Y: {1}, Z: {2}, yaw: {3}, pitch: {4}", new Object[]{x, y, z, yaw, pitch});
    }
    
}