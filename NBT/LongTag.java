package NBT;

public class LongTag extends Tag<Long> {

    private final long value;

    public LongTag(String name, long value) {
        super(name, TagType.TAG_LONG);

        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
