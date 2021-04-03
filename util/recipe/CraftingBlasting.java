package util.recipe;

import util.Slot;

public class CraftingBlasting extends FireRecipe {
    
    public CraftingBlasting(String type, String id, String group, Ingredient ingredient, Slot result, float xp, int cookingTime) {
        super(type, id, group, ingredient, result, xp, cookingTime);
    }
}