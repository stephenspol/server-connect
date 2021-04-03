package util.recipe;

import util.Slot;

public class CraftingShaped extends CraftingRecipe {
    
    public CraftingShaped(String type, String id, String group, Ingredient[] ingredients, Slot result) {
        super(type, id, group, ingredients, result);
    }
}
