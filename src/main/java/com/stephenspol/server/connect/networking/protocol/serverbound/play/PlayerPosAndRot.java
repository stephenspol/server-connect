package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x13 | C->S
public class PlayerPosAndRot {

    private PlayerPosAndRot() {}

    public static byte[] execute(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x13;

        buffer.writeByte(packetID);

        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);

        buffer.writeFloat(yaw);
        buffer.writeFloat(pitch);

        buffer.writeBoolean(onGround);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}, Yaw: {3}, Pitch {4}, On Ground: {5}", new Object[]{x, y, z, yaw, pitch, onGround});

        return buffer.getPacket();
    }
    
}