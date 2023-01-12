package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.nbt.Tag;
import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x09 | S->C
public class BlockEntityData {

    private BlockEntityData() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int[] location = buffer.readPos();

        short action = buffer.readUnsignedByte();

        Tag<?> nbt = buffer.readNBT();

        log.log(packetInfo, "Block located at X: {0}, Y: {1}, Z: {2}", new Object[]{location[0], location[1], location[2]});
        log.log(packetInfo, "Action: {0}, NBT Data: {1}", new Object[]{action, nbt});
    }
    
}