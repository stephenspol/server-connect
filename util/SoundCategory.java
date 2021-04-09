package util;

public enum SoundCategory {
    MASTER(0),
    MUSIC(1),
    RECORD(2),
    WEATHER(3),
    BLOCK(4),
    HOSTILE(5),
    NEUTRAL(6),
    PLAYER(7),
    AMBIENT(8),
    VOICE(9);

    private final int id;
    private final String name;

    private static final SoundCategory[] BY_ID;

    static {
        BY_ID = new SoundCategory[10];
        for (SoundCategory s : SoundCategory.values()) {
            BY_ID[s.getId()] = s;
        }
    }

    private SoundCategory(int id) {
        this.id = id;
        this.name = name().toLowerCase();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static SoundCategory getById(int id) {
        if (id >= 0 && id < BY_ID.length) {
            SoundCategory soundId = BY_ID[id];
            if (soundId == null) {
                throw new IllegalArgumentException("Sound id " + id + " is unknown!");
            }
            return soundId;
        } else {
            throw new IndexOutOfBoundsException("Sound id " + id + " is out of bounds!");
        }
    }
}