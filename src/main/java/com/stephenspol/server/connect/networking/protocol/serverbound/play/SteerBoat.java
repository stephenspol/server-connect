package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x17 | C->S
public class SteerBoat {

    private SteerBoat() {}

    public static byte[] execute(boolean leftPaddle, boolean rightPaddle) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x17;

        buffer.writeByte(packetID);

        buffer.writeBoolean(leftPaddle);
        buffer.writeBoolean(rightPaddle);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Left Paddle Turning: {0}, Right Paddle Turning: {1}", new Object[]{leftPaddle, rightPaddle});

        return buffer.getPacket();
    }
    
}