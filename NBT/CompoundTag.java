package NBT;

import java.util.Map;

public class CompoundTag extends Tag<Map<String, Tag<?>>> {

    private final Map<String, Tag<?>> value;

    public CompoundTag(String name, Map<String, Tag<?>> value) {
        super(name, TagType.TAG_COMPOUND);

        this.value = value;
    }

    public Map<String, Tag<?>> getValue() {
        return value;
    }
    
}
