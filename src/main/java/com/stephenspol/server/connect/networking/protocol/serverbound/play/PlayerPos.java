package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x12 | C->S
public class PlayerPos {

    private PlayerPos() {}

    public static byte[] execute(double x, double y, double z, boolean onGround) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x12;

        buffer.writeByte(packetID);

        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);

        buffer.writeBoolean(onGround);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}, On Ground: {3}", new Object[]{x, y, z, onGround});

        return buffer.getPacket();
    }
    
}