package util.recipe;

import util.Slot;

public abstract class FireRecipe extends Recipe {

    protected String group;
    protected Ingredient ingredient;
    protected Slot result;
    protected float xp;
    protected int cookingTime;

    protected FireRecipe(String type, String id, String group, Ingredient ingredient, Slot result, float xp, int cookingTime) {
        super(type, id);

        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.xp = xp;
        this.cookingTime = cookingTime;
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

    public float getXP() {
        return xp;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Fire Recipe: [Type: " + type + "] [ID: " + id + "]\n");
        sb.append("[Group: " + group + "] " + ingredient);

        sb.append(" [Result: " + result + "]\n");
        sb.append("[XP: " + xp + "] [Cooking Time: " + cookingTime + "]");

        return sb.toString();
    }
    
}
