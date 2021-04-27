package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

import util.Slot;
import util.Trade;

// Packet ID 0x26 | S->C
public class TradeList {

    private TradeList() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int windowID = buffer.readVarInt();

        log.log(packetInfo, "Trade Window ID: {0}", windowID);

        byte length = buffer.readByte();

        Trade[] trades = new Trade[length];

        for (int i = 0; i < length; i++) {
            Slot input1 = buffer.readSlot();
            Slot input2 = null;
            Slot output = buffer.readSlot();

            boolean secondItem = buffer.readBoolean();

            if (secondItem) input2 = buffer.readSlot();

            boolean tradeDisabled = buffer.readBoolean();
            int tradeUses = buffer.readInt();
            int maxTradeUses = buffer.readInt();

            int xp = buffer.readInt();

            int specialPrice = buffer.readInt();
            float priceMultiplier = buffer.readFloat();
            int demand = buffer.readInt();

            trades[i] = new Trade(input1, output, input2, tradeDisabled, tradeUses, maxTradeUses, xp, specialPrice, priceMultiplier, demand);

            log.log(packetInfo, "Trade {0}: {1}", new Object[]{i, trades[i]});
        }

        int villagerLvl = buffer.readVarInt();
        int xp = buffer.readVarInt();

        boolean isRegularVillager = buffer.readBoolean();
        boolean canStock = buffer.readBoolean();

        log.log(packetInfo, "Villager Level: {0}, XP: {1}, Is Regular Villager: {2}, Can Stock {3}", new Object[]{villagerLvl, xp, isRegularVillager, canStock});
    }
    
}