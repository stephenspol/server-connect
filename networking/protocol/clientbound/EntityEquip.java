package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;
import util.Equipment;
import util.Slot;

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