package util;

public enum SoundCategory {
    MASTER,
    MUSIC,
    RECORD,
    WEATHER,
    BLOCK,
    HOSTILE,
    NEUTRAL,
    PLAYER,
    AMBIENT,
    VOICE;

    private final String name;

    private static final SoundCategory[] BY_ID = values();

    private SoundCategory() {
        this.name = name().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public static SoundCategory getById(int id) {
        if (id >= 0 && id < BY_ID.length) {
            SoundCategory sound = BY_ID[id];
            if (sound == null) {
                throw new IllegalArgumentException("Sound id " + id + " is unknown!");
            }
            return sound;
        } else {
            throw new IndexOutOfBoundsException("Sound id " + id + " is out of bounds!");
        }
    }
}