package util.recipe;

import java.util.HashMap;
import java.util.Map;

public enum RecipeType {
    CRAFTING_SHAPELESS(CraftingShapeless.class),
    CRAFTING_SHAPED(CraftingShaped.class),
    CRAFTING_SPECIAL_ARMORDYE(CraftingSpecialArmorDye.class),
    CRAFTING_SPECIAL_BOOKCLONING(CraftingSpecialBookCloning.class),
    CRAFTING_SPECIAL_MAPCLONING(CraftingSpecialMapCloning.class),
    CRAFTING_SPECIAL_MAPEXTENDING(CraftingSpecialMapExtending.class),
    CRAFTING_SPECIAL_FIREWORK_ROCKET(CraftingSpecialFireworkRocket.class),
    CRAFTING_SPECIAL_FIREWORK_STAR(CraftingSpecialFireworkStar.class),
    CRAFTING_SPECIAL_FIREWORK_STAR_FADE(CraftingSpecialFireworkStarFade.class),
    CRAFTING_SPECIAL_REPAIRITEM(CraftingSpecialRepairItem.class),
    CRAFTING_SPECIAL_TIPPEDARROW(CraftingSpecialTippedArrow.class),
    CRAFTING_SPECIAL_BANNERDUPLICATE(CraftingSpecialBannerDuplicate.class),
    CRAFTING_SPECIAL_BANNERADDPATTERN(CraftingSpecialBannerAddPattern.class),
    CRAFTING_SPECIAL_SHIELDDECORATION(CraftingSpecialShieldDecoration.class),
    CRAFTING_SPECIAL_SHULKERBOXCOLORING(CraftingSpecialShulkerBoxColoring.class),
    CRAFTING_SPECIAL_SUSPICIOUSSTEW(CraftingSpecialSuspiciousStew.class),
    SMELTING(CraftingSmelting.class),
    BLASTING(CraftingBlasting.class),
    SMOKING(CraftingSmoking.class),
    CAMPFIRE_COOKING(CraftingCampfireCooking.class),
    STONECUTTING(CraftingStonecutting.class),
    SMITHING(CraftingSmithing.class);

    private final String recipeName;
    private final Class<?> clazz;

    private static final Map<Class<?>, RecipeType> BY_CLASS = new HashMap<>();
    private static final Map<String, RecipeType> BY_NAME = new HashMap<>();

    static {
        for (RecipeType type : RecipeType.values()) {
            BY_CLASS.put(type.getRecipeClass(), type);
            BY_NAME.put(type.getRecipeName(), type);
        }
    }

    private RecipeType(Class<?> clazz) {
        recipeName = "minecraft:" + name().toLowerCase();
        this.clazz = clazz;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Class<?> getRecipeClass() {
        return clazz;
    }

    public static RecipeType getByClass(Class<?> clazz) {
        RecipeType ret = BY_CLASS.get(clazz);
        if (ret == null) {
            throw new IllegalArgumentException("Recipe type " + clazz + " is unknown!");
        }
        return ret;
    }

    public static RecipeType getByName(String name) {
        RecipeType type = BY_NAME.get(name);
        if (type == null) {
            throw new IllegalArgumentException("Recipe type " + name + " is unknown!");
        }
        return type;
    }
}
