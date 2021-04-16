package nbt;

public class FloatTag extends Tag<Float> {
    
    private final float value;

    public FloatTag(String name, float value) {
        super(name, TagType.TAG_FLOAT);
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TAG_FLOAT('" + name + "'): " + value;
    }

}
