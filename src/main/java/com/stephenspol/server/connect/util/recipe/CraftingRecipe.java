package com.stephenspol.server.connect.util.recipe;

import com.stephenspol.server.connect.util.Slot;

public abstract class CraftingRecipe extends Recipe {

    protected String group;
    protected Ingredient[] ingredients;
    protected Slot result;

    protected CraftingRecipe(String type, String id, String group, Ingredient[] ingredients, Slot result) {
        super(type, id);

        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
    }

    public String getGroup() {
        return group;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public Slot getResult() {
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Crafting Recipe: [Type: " + type + "] [ID: " + id + "]\n");
        sb.append("[Group: " + group + "] [Ingredients: ");

        for (int i = 0; i < ingredients.length; i++) {
            sb.append(ingredients[i]);
        }

        sb.append(" [Result: " + result + "]");

        return sb.toString();
    }
    
}
