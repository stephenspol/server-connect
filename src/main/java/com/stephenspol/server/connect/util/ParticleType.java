package com.stephenspol.server.connect.util;

public enum ParticleType {
    AMBIENT_ENTITY_EFFECT,
    ANGRY_VILLAGER,
    BARRIER,
    BLOCK,
    BUBBLE,
    CLOUD,
    CRIT,
    DAMAGE_INDICATOR,
    DRAGON_BREATH,
    DRIPPING_LAVA,
    FALLING_LAVA,
    LANDING_LAVA,
    DRIPPING_WATER,
    FALLING_WATER,
    DUST,
    EFFECT,
    ELDER_GUARDIAN,
    ENCHANTED_HIT,
    ENCHANT,
    END_ROD,
    ENTITY_EFFECT,
    EXPLOSION_EMITTER,
    EXPLOSION,
    FALLING_DUST,
    FIREWORK,
    FISHING,
    FLAME,
    FLASH,
    HAPPY_VILLAGER,
    COMPOSTER,
    HEART,
    INSTANT_EFFECT,
    ITEM,
    ITEM_SLIME,
    ITEM_SNOWBALL,
    LARGE_SMOKE,
    LAVA,
    MYCELIUM,
    NOTE,
    POOF,
    PORTAL,
    RAIN,
    SMOKE,
    SNEEZE,
    SPIT,
    SQUID_INK,
    SWEEP_ATTACK,
    TOTEM_OF_UNDYING,
    UNDERWATER,
    SPLASH,
    WITCH,
    BUBBLE_POP,
    CURRENT_DOWN,
    BUBBLE_COLUMN_UP,
    NAUTILUS,
    DOLPHIN,
    CAMPFIRE_COSY_SMOKE,
    CAMPFIRE_SIGNAL_SMOKE,
    DRIPING_HONEY,
    FALLING_HONEY,
    LANDING_HONEY,
    FALLING_NECTAR;

    private final String name;

    private static final ParticleType[] BY_ID = values();

    private ParticleType() {
        name = "minecraft:" + name().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public static ParticleType getById(int id) {
        if (id >= 0 && id < BY_ID.length) {
            ParticleType particle = BY_ID[id];
            if (particle == null) {
                throw new IllegalArgumentException("Particle id " + id + " is unknown!");
            }
            return particle;
        } else {
            throw new IndexOutOfBoundsException("Particle id " + id + " is out of bounds!");
        }
    }
}
