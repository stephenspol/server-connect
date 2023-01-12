package com.stephenspol.server.connect.util;

import java.util.UUID;

public class Modifier {
    
    private final UUID uuid;
    private double amount;
    
    private byte operation;

    public Modifier(UUID uuid, double amount, byte operation) {
        this.uuid = uuid;
        this.amount = amount;
        this.operation = operation;
    }

    public UUID getUUID() {
        return uuid;
    }

    public double getAmount() {
        return amount;
    }

    public byte getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "UUID: " + uuid + ", Amount: " + amount + ", Operation: " + operation;
    }
}
