package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x0F | C->S
public class GenerateStructure {

    private GenerateStructure() {}

    public static byte[] execute(int[] pos, int levels, boolean keepJigsaws) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x03;

        buffer.writeByte(packetID);

        buffer.writePos(pos);

        buffer.writeVarInt(levels);

        buffer.writeBoolean(keepJigsaws);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});

        log.log(packetInfo, "Levels: {0}, Keep Jigsaws: {1}", new Object[]{levels, keepJigsaws});

        return buffer.getPacket();
    }
    
}