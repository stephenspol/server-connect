package util;

import nbt.Tag;

public class Slot {

    private int id;
    private byte count;
    private Tag<?> data;

    public Slot(int id, byte count, Tag<?> data) {
        this.id = id;
        this.count = count;
        this.data = data;
    }

    public int getItemId() {
        return id;
    }

    public byte getCount() {
        return count;
    }

    public Tag getData() {
        return data;
    }
    
    @Override
    public String toString() {
        return "Item id: " + id + ", Count: " + count + ", Data: " + data;
    }
}
