package com.stephenspol.server.connect.util;

import java.util.Arrays;

public class Property {
    
    private final String key;
    private double value;
    
    private Modifier[] modifiers;

    public Property(String key, double value, Modifier[] modifiers) {
        this.key = key;
        this.value = value;
        this.modifiers = modifiers;
    }

    public String getKey() {
        return key;
    }

    public double getValue() {
        return value;
    }

    public Modifier[] getModifier() {
        return modifiers;
    }

    @Override
    public String toString() {
        return "Key: " + key + ", Value: " + value + ", Modifiers: " + Arrays.toString(modifiers);
    }

}
