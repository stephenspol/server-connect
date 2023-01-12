package com.stephenspol.server.connect.util;

import java.util.HashMap;
import java.util.Map;

public enum EffectType {
    DISPENSER_DISPENSES(1000),
    DISPENSER_FAILS_TO_DISPENSE(1001),
    DISPENSER_SHOOT(1002),
    ENDER_EYE_LAUNCHED(1003),
    FIREWORK_SHOT(1004),
    IRON_DOOR_OPENED(1005),
    WOODEN_DOOR_OPENED(1006),
    WOODEN_TRAPDOOR_OPENED(1007),
    FENCE_GATE_OPENED(1008),
    FIRE_EXTINGUSIHED(1009),
    PLAY_RECORD(1010),
    IRON_DOOR_CLOSED(1011),
    WOODEN_DOOR_CLOSED(1012),
    WOODEN_TRAPDOOR_CLOSED(1013),
    FENCE_GATE_CLOSED(1014),
    GHAST_WARNS(1015),
    GHAST_SHOOTS(1016),
    ENDERDRAGON_SHOOTS(1017),
    BLAZE_SHOOTS(1018),
    ZOMBIE_ATTACKS_WOOD_DOOR(1019),
    ZOMBIE_ATTACKS_IRON_DOOR(1020),
    ZOMBIE_BREAKS_WOOD_DOOR(1021),
    WITHER_BREAKS_BLOCK(1022),
    WITHER_SHOOTS(1023),
    BAT_TAKES_OFF(1024),
    ZOMBIE_INFECTS(1025),
    ZOMBIE_VILLAGER_CONVERTED(1026),
    ENDER_DRAGON_DEATH(1027),
    ANVIL_DESTROYED(1028),
    ANVIL_USED(1029),
    ANVIL_LANDED(1030),
    PROTAL_TRAVEL(1031),
    CHORUS_FLOWER_GROWN(1032),
    CHORUS_FLOWER_DIED(1033),
    BREWING_STAND_BREWED(1034),
    IRON_TRAPDOOR_OPENED(1035),
    END_PORTAL_CREATED_IN_OVERWORLD(1036),
    PHANTOM_BITES(1037),
    ZOMBIE_CONVERTS_TO_DROWNED(1038),
    HUSK_CONVERTS_TO_ZOMBIE_BY_DROWNING(1039),
    GRINDSTONE_USED(1040),
    BOOK_PAGE_TURNED(1041),

    COMPOSTER_COMPOSTS(1500),
    LAVA_CONVERTS_BLOCK(1501),
    REDSTONE_TORCH_BURNS_OUT(10502),
    ENDER_EYE_PLACED(1053),

    SPAWNS_SMOKE_PARTICLES(2000),
    BLOCK_BREAK(2001),
    SPLASH_POTION(2002),
    EYE_OF_ENDER_BREAK_ANIMATION(2003),
    MOB_SPAWN_PARTICLE_EFFECT(2004),
    BONEMEAL_PARTICLES(2005),
    DRAGON_BREATH(2006),
    INSTANT_POTION(2007),
    ENDER_DRAGON_DESTROYS_BLOCK(2008),
    WET_SPONGE_VAPORIZES_IN_NETHER(2009),

    END_GATEWAY_SPAWN(3000),
    ENDERDRAGON_GROWL(3001);
    

    private final int id;
    private final String name;

    private static final Map<Integer, EffectType> BY_ID = new HashMap<>();

    static {
        for (EffectType effect : values()) {
            BY_ID.put(effect.getId(), effect);
        }
    }

    private EffectType(int id) {
        this.id = id;

        this.name = name().substring(0, 1) + name().substring(1).replace("_", " ").toLowerCase();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static EffectType getById(int id) {
        EffectType effect = BY_ID.get(id);
        if (effect == null) {
            throw new IllegalArgumentException("Effect id " + id + " is unknown!");
        }
        return effect;
    }
}
