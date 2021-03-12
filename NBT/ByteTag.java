package NBT;

public class ByteTag extends Tag<Byte> {

    private final byte value;

    public ByteTag(String name, boolean value) {
        this(name, (byte) (value ? 1 : 0));
    }

    public ByteTag(String name, byte value) {
        super(name, TagType.TAG_BYTE);
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

    public boolean getBoolean() {
        return value != 0;
    }

}