package util;

import java.util.HashMap;
import java.util.Map;

public enum EffectType {
    DISPENSER_DISPENSES(1000),
    DISPENSER_FAILS_TO_DISPENSE,
    DISPENSER_SHOOT,
    ENDER_EYE_LAUNCHED,
    FIREWORK_SHOT,
    IRON_DOOR_OPENED,
    WOODEN_DOOR_OPENED,
    WOODEN_TRAPDOOR_OPENED,
    FENCE_GATE_OPENED,
    FIRE_EXTINGUSIHED,
    PLAY_RECORD,
    IRON_DOOR_CLOSED,
    WOODEN_DOOR_CLOSED,
    WOODEN_TRAPDOOR_CLOSED,
    FENCE_GATE_CLOSED,
    GHAST_WARNS,
    GHAST_SHOOTS,
    ENDERDRAGON_SHOOTS,
    BLAZE_SHOOTS,
    ZOMBIE_ATTACKS_WOOD_DOOR,
    ZOMBIE_ATTACKS_IRON_DOOR,
    ZOMBIE_BREAKS_WOOD_DOOR,
    WITHER_BREAKS_BLOCK,
    WITHER_SHOOTS,
    BAT_TAKES_OFF,
    ZOMBIE_INFECTS,
    ZOMBIE_VILLAGER_CONVERTED,
    ENDER_DRAGON_DEATH,
    ANVIL_DESTROYED,
    ANVIL_USED,
    ANVIL_LANDED,
    PROTAL_TRAVEL,
    CHORUS_FLOWER_GROWN,
    CHORUS_FLOWER_DIED,
    BREWING_STAND_BREWED,
    IRON_TRAPDOOR_OPENED,
    END_PORTAL_CREATED_IN_OVERWORLD,
    PHANTOM_BITES,
    ZOMBIE_CONVERTS_TO_DROWNED,
    HUSK_CONVERTS_TO_ZOMBIE_BY_DROWNING,
    GRINDSTONE_USED,
    BOOK_PAGE_TURNED,

    COMPOSTER_COMPOSTS(1500),
    LAVA_CONVERTS_BLOCK,
    REDSTONE_TORCH_BURNS_OUT,
    ENDER_EYE_PLACED,

    SPAWNS_SMOKE_PARTICLES(2000),
    BLOCK_BREAK,
    SPLASH_POTION,
    EYE_OF_ENDER_BREAK_ANIMATION,
    MOB_SPAWN_PARTICLE_EFFECT,
    BONEMEAL_PARTICLES,
    DRAGON_BREATH,
    INSTANT_POTION,
    ENDER_DRAGON_DESTROYS_BLOCK,
    WET_SPONGE_VAPORIZES_IN_NETHER,

    END_GATEWAY_SPAWN(3000),
    ENDERDRAGON_GROWL;
    

    private final int id;
    private final String name;

    private static final Map<Integer, EffectType> BY_ID = new HashMap<>();

    static {
        for (EffectType effect : values()) {
            BY_ID.put(effect.getId(), effect);
        }
    }

    private EffectType() {
        // Incriment last ID by 1
        this(values()[values().length - 1].getId() + 1);
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
