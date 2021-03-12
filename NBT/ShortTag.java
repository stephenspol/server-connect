package NBT;

public class ShortTag extends Tag<Short> {

    private short value;

    public ShortTag(String name, short value) {
        super(name, TagType.TAG_SHORT);
        this.value = value;
    }
    
    public Short getValue() {
        return value;
    }
}
