package NBT;

public class LongArrayTag extends Tag<long[]> {

    private final long[] value;

    public LongArrayTag(String name, long[] value) {
        super(name, TagType.TAG_LONG_ARRAY);
        this.value = value;
    }

    public long[] getValue() {
        return value;
    }
    
}
