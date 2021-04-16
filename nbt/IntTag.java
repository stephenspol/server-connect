package nbt;

public class IntTag extends Tag<Integer> {

    private int value;

    public IntTag(String name, int value) {
        super(name, TagType.TAG_INT);
        this.value = value;
    }
    
    public Integer getValue() {
        return value;
    }

    public String toString() {
        return "TAG_INT('" + name + "'): " + value; 
    }
}
