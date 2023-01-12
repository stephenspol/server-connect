package com.stephenspol.server.connect.util.recipe;

import com.stephenspol.server.connect.util.Slot;

public class CraftingSmithing extends Recipe {

    private Ingredient base;
    private Ingredient addition;
    private Slot result;
    
    public CraftingSmithing(String type, String id, Ingredient base, Ingredient addition, Slot result) {
        super(type, id);

        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public Ingredient getBaseIngredient() {
        return base;
    }

    public Ingredient getAdditionIngredient() {
        return addition;
    }

    public Slot getResult() {
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Smithing Crafting: [Type: " + type + "] [ID:" + id + "]\n");
        sb.append("[Base: " + base);

        sb.append("] [Addition:" + addition);

        sb.append("]\n[Result: " + result + "]");

        return sb.toString();
    }
}
