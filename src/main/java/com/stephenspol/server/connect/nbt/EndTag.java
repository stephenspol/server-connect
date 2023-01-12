package com.stephenspol.server.connect.nbt;

public class EndTag extends Tag<Object> {

    public EndTag() {
        super(TagType.TAG_END);
    }
    
    public Object getValue() {
        return null;
    }

    public String toString() {
        return "TAG_END";
    }
}
