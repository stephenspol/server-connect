package NBT;

public class StringTag extends Tag<String> {

    private final String value;

    public StringTag(String name, String value) {
        super(name, TagType.TAG_STRING);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TAG_STRING('" + name + "'): '" + value + "'";
    }
}
