package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x16 | C->S
public class VehicleMove {

    private VehicleMove() {}

    public static byte[] execute(double x, double y, double z, float yaw, float pitch) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x16;

        buffer.writeByte(packetID);

        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);

        buffer.writeFloat(yaw);
        buffer.writeFloat(pitch);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}, Yaw: {3}, Pitch: {4}", new Object[]{x, y, z, yaw, pitch});
        
        return buffer.getPacket();
    }
    
}