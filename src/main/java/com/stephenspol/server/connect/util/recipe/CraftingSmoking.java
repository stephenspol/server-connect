package com.stephenspol.server.connect.util.recipe;

import com.stephenspol.server.connect.util.Slot;

public class CraftingSmoking extends FireRecipe {
    
    public CraftingSmoking(String type, String id, String group, Ingredient ingredient, Slot result, float xp, int cookingTime) {
        super(type, id, group, ingredient, result, xp, cookingTime);
    }
}