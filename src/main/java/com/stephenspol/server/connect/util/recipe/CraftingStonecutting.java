package com.stephenspol.server.connect.util.recipe;

import com.stephenspol.server.connect.util.Slot;

public class CraftingStonecutting extends Recipe {

    private String group;
    private Ingredient ingredient;
    private Slot result;

    public CraftingStonecutting(String type, String id, String group, Ingredient ingredient, Slot result) {
        super(type, id);

        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
    }

    public String getGroup() {
        return group;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Slot getResult() {
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Stone Cutting Crafting: [Type: " + type + "] [ID: " + id + "]\n");
        sb.append("[Group: " + group + "] " + ingredient);

        sb.append(" [Result: " + result + "]");

        return sb.toString();
    }
    
}
