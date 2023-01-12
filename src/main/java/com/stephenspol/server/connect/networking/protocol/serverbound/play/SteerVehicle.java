package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x1D | C->S
public class SteerVehicle {

    private SteerVehicle() {}

    public static byte[] execute(float sideways, float forward, byte flags) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x1D;

        buffer.writeByte(packetID);

        buffer.writeFloat(sideways);

        buffer.writeFloat(forward);

        buffer.writeByte(flags);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Sideways: {0}, Forward: {1}, Flags: {2}", new Object[]{sideways, forward, flags});

        return buffer.getPacket();
    }
    
}