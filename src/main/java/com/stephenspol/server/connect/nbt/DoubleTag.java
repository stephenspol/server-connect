package com.stephenspol.server.connect.nbt;

public class DoubleTag extends Tag<Double> {
    
    private final double value;

    public DoubleTag(String name, double value) {
        super(name, TagType.TAG_DOUBLE);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TAG_DOUBLE('" + name + "'): " + value;
    }

}
