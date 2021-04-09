package util;

import java.util.Arrays;

public class Tag {

    private String name;
    private int[] entries;
    
    public Tag(String name, int[] entries) {
        this.name = name;
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public int[] getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Entries: " + Arrays.toString(entries);
    }

}
