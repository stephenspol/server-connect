package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

import com.stephenspol.server.connect.util.recipe.Recipe;

// Packet ID 0x5A | S->C
public class DeclareRecipes {

    private DeclareRecipes() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException {
        int numOfRecipes = buffer.readVarInt();
        Recipe[] recipes = new Recipe[numOfRecipes];

        log.log(packetInfo, "Recipes({0}):", numOfRecipes);

        for (int i = 0; i < recipes.length; i++) {
            recipes[i] = buffer.readRecipe();

            log.log(packetInfo, " [{0}]\n", recipes[i]);
        }
    }
    
}