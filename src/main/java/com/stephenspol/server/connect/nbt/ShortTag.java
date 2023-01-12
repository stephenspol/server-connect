package com.stephenspol.server.connect.nbt;

public class ShortTag extends Tag<Short> {

    private short value;

    public ShortTag(String name, short value) {
        super(name, TagType.TAG_SHORT);
        this.value = value;
    }
    
    public Short getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TAG_SHORT('" + name + "'): " + value; 
    }
}
