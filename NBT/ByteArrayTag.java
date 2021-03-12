package NBT;

public class ByteArrayTag extends Tag<byte[]> {
    
    private final byte[] value;

    public ByteArrayTag(String name, byte[] value) {
        super(name, TagType.TAG_BYTE_ARRAY);
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }
}
