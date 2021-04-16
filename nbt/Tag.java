package nbt;

public abstract class Tag<T> {
    
    protected final String name;
    private final TagType tagType;

    protected Tag(TagType tagType) {
        this("", tagType);
    }

    protected Tag(String name, TagType tagType) {
        this.name = name;
        this.tagType = tagType;
    }

    public String getName() {
        return name;
    }

    public TagType getTagType() {
        return tagType;
    }

    public abstract T getValue();
}
