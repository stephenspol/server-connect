package com.stephenspol.server.connect.nbt;

public class LongArrayTag extends Tag<long[]> {

    private final long[] value;

    public LongArrayTag(String name, long[] value) {
        super(name, TagType.TAG_LONG_ARRAY);
        this.value = value;
    }

    public long[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TAG_LONG_ARRAY('" + name + "'): [" + value.length + " longs]";
    }
    
}
