package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x0D | C->S
public class QueryEntityNBT {

    private QueryEntityNBT() {}

    public static byte[] execute(int transactionID, int entityID) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x0D;

        buffer.writeByte(packetID);

        buffer.writeVarInt(transactionID);

        buffer.writeVarInt(entityID);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Transaction ID: {0}, Entity ID: {1}", new Object[]{transactionID, entityID});

        return buffer.getPacket();
    }
    
}