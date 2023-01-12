package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x29 | C->S
public class UpdateJigsawBlock {

    private UpdateJigsawBlock() {}

    public static byte[] execute(int[] pos, String name, String target, String pool, String finalState, String jointType) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x29;

        buffer.writeByte(packetID);

        buffer.writePos(pos);

        buffer.writeString(name);
        buffer.writeString(target);
        buffer.writeString(pool);

        buffer.writeString(finalState);
        buffer.writeString(jointType);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});

        log.log(packetInfo, "Name: {0}, Target: {1}, Pool: {2}", new Object[]{name, target, pool});
        log.log(packetInfo, "Final State: {0}, Joint Type: {1}", new Object[]{finalState, jointType});

        return buffer.getPacket();
    }
    
}