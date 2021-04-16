package nbt;

public class ByteArrayTag extends Tag<byte[]> {
    
    private final byte[] value;

    public ByteArrayTag(String name, byte[] value) {
        super(name, TagType.TAG_BYTE_ARRAY);
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TAG_BYTE_ARRAY('" + name + "'): [" + value.length + " bytes]";
    }
}
