package NBT;

public class IntArrayTag extends Tag<int[]> {

    private final int[] value;

    public IntArrayTag(String name, int[] value) {
        super(name, TagType.TAG_INT_ARRAY);
        this.value = value;
    }

    public int[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TAG_INT_ARRAY('" + name + "'): [" + value.length + " integers]";
    }
    
}
