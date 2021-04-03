package util.recipe;

import util.Slot;

public class Ingredient {

    private Slot[] slots;

    public Ingredient(Slot[] slots) {
        this.slots = slots;
    }

    public Slot[] getSlots() {
        return slots;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[Ingredient:");
        for (int i = 0; i < slots.length; i++) {
            sb.append(" [" + slots[i] + "]");
        }
        sb.append("]");

        return sb.toString();
    }
}
