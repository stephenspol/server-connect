package nbt;

import java.util.Collections;
import java.util.List;

public class ListTag<T extends Tag<?>> extends Tag<List<T>>{
    
    private final Class<T> type;
    
    private final List<T> value;

    public ListTag(String name, Class<T> type, List<T> value) {
        super(name, TagType.TAG_LIST);
        this.type = type;
        this.value = Collections.unmodifiableList(value);
    }

    public Class<T> getElementType() {
        return type;
    }

    public List<T> getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("TAG_LIST('" + name + "' [" + type.getName() + "]): " + value.size() + " entries");

        for (int i = 0; i < value.size(); i++) {
            sb.append("\n\t" + value.get(i));
        }

        return sb.toString();
    }

}
