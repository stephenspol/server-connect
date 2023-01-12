package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x0A | S->C
public class BlockAction {

    private BlockAction() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int[] location = buffer.readPos();

        short actionID = buffer.readUnsignedByte();
        short actionParam = buffer.readUnsignedByte();

        int blockID = buffer.readVarInt();

        log.log(packetInfo, "Block ID {0} at location X: {1}, Y: {2}, Z: {3}", new Object[]{blockID, location[0], location[1], location[2]});
        log.log(packetInfo, "Action ID: {0}, Action Parameter: {1}", new Object[]{actionID, actionParam});
    }
    
}