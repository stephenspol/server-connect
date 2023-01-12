package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

import com.stephenspol.server.connect.util.Equipment;
import com.stephenspol.server.connect.util.Slot;

// Packet ID 0x47 | S->C
public class EntityEquip {

    private EntityEquip() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();

        log.log(packetInfo, "Equip Entity ID {0}", entityID);

        Equipment[] equipments = new Equipment[6];

        for (int i = 0;; i++) {
            byte slot = buffer.readByte();

            Slot item = buffer.readSlot();

            equipments[i] = new Equipment(slot, item);

            log.log(packetInfo, "Equipment {0}: {1}", new Object[]{i, equipments[i]});

            if (slot >= 0) break;
        }
    }
    
}