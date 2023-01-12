package com.stephenspol.server.connect.nbt;

import java.util.Map;

public class CompoundTag extends Tag<Map<String, Tag<?>>> {

    private final Map<String, Tag<?>> value;

    public CompoundTag(String name, Map<String, Tag<?>> value) {
        super(name, TagType.TAG_COMPOUND);

        this.value = value;
    }

    public Map<String, Tag<?>> getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("TAG_COMPOUND('" + name + "'): " + value.size() + " entries");

        for (Tag<?> tag : value.values()) {
            sb.append("\n\t" + tag);
        }

        return sb.toString();
    }
    
}
