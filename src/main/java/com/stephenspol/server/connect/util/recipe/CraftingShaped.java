package com.stephenspol.server.connect.util.recipe;

import com.stephenspol.server.connect.util.Slot;

public class CraftingShaped extends CraftingRecipe {
    
    public CraftingShaped(String type, String id, String group, Ingredient[] ingredients, Slot result) {
        super(type, id, group, ingredients, result);
    }
}
