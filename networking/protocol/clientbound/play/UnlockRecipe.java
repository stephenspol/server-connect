package networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.Arrays;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x35 | S->C
public class UnlockRecipe {

    private UnlockRecipe() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        // 0: Initiate | 1: Add | 2: Remove
        int action = buffer.readVarInt();

        boolean craftingOpen = buffer.readBoolean();
        boolean craftingFilter = buffer.readBoolean();

        boolean smeltingOpen = buffer.readBoolean();
        boolean smeltingFilter = buffer.readBoolean();

        boolean blastFurnaceOpen = buffer.readBoolean();
        boolean blastFurnaceFilter = buffer.readBoolean();

        boolean smokerOpen = buffer.readBoolean();
        boolean smokerFilter = buffer.readBoolean();

        int length = buffer.readVarInt();

        String[] recipeIDs = new String[length];

        for (int i = 0; i < length; i++) {
            recipeIDs[i] = buffer.readString();
        }

        String[] recipeIDs2 = new String[0];

        if (action == 0) {
            length = buffer.readVarInt();

            recipeIDs2 = new String[length];

            for (int i = 0; i < length; i++) {
                recipeIDs2[i] = buffer.readString();
            }
        }

        log.log(packetInfo, "Action: {0}", action);
        
        log.log(packetInfo, "Crafting Recipe Book Open: {0}", craftingOpen);
        log.log(packetInfo, "Crafting Recipe Book Filter Active: {0}", craftingFilter);

        log.log(packetInfo, "Smelting Recipe Book Open: {0}", smeltingOpen);
        log.log(packetInfo, "Smelting Recipe Book Filter Active: {0}", smeltingFilter);

        log.log(packetInfo, "Blast Furnace Recipe Book Open: {0}", blastFurnaceOpen);
        log.log(packetInfo, "Blast Furnace Recipe Book Filter Active: {0}", blastFurnaceFilter);

        log.log(packetInfo, "Smoker Recipe Book Open: {0}", smokerOpen);
        log.log(packetInfo, "Smoker Recipe Book Filter Active: {0}", smokerFilter);

        log.log(packetInfo, "Recipe IDs: {0}", Arrays.toString(recipeIDs));

        if (action == 0) log.log(packetInfo, "Recipe IDs: {0}", Arrays.toString(recipeIDs2));
    }
    
}