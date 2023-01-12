package com.stephenspol.server.connect.util;

import java.util.UUID;

public class Player {

    private final UUID uuid;
    private final String name;

    private final PlayerProperty[] properties;

    private int gameMode;
    private int ping;

    private String customName;

    public Player(UUID uuid, String name, PlayerProperty[] properties, int gameMode, int ping) {
        this(uuid, name, properties, gameMode, ping, null);
    }


    public Player(UUID uuid, String name, PlayerProperty[] properties, int gameMode, int ping, String customName) {
        this.uuid = uuid;
        this.name = name;

        this.properties = properties;

        this.gameMode = gameMode;
        this.ping = ping;

        this.customName = customName;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public PlayerProperty[] getProperties() {
        return properties;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void updateGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getPing() {
        return ping;
    }

    public void updatePing(int ping) {
        this.ping = ping;
    }

    public int getPingBarValue() {
        if (ping >= 1000) {
            return 1;
        } else if (ping >= 600) {
            return 2;
        } else if (ping >= 300) {
            return 3;
        } else if (ping >= 150) {
            return 4;
        } else if (ping >= 0) {
            return 5;
        } else {
            return 0;
        }
    }

    public String getCustomName() {
        return customName;
    }

    public void updateCustomName(String customName) {
        this.customName = customName;
    }

    public boolean hasCustomName() {
        return customName != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("UUID: " + uuid + ", Name: " + name + ", Properties:\n");

        for (PlayerProperty p: properties) {
            sb.append(p + "\n");
        }

        sb.append("Game Mode: " + gameMode + ", Ping: " + ping);
        sb.append(hasCustomName()? ", Custom Name: " + customName : ", Has Custom Name: false");

        return sb.toString();
    }
    
}
