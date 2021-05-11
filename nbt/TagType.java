package nbt;

import java.util.HashMap;
import java.util.Map;

/** Enum of different NBT Tags. */
public enum TagType {
    /** {@code TAG_END} is only used to signify the end of a {@code TAG_COMPOUND}. */
    TAG_END("TAG_END", EndTag.class),
    /** A single signed byte. */
    TAG_BYTE("TAG_BYTE", ByteTag.class),
    /** A 16 bit signed integer. */
    TAG_SHORT("TAG_SHORT", ShortTag.class),
    /** A 32 bit signed integer. */
    TAG_INT("TAG_INT", IntTag.class),
    /** A 64 bit signed integer */
    TAG_LONG("TAG_LONG", LongTag.class),
    /** A single-percision floating point number. */
    TAG_FLOAT("TAG_FLOAT", FloatTag.class),
    /** A double-percision floating point number. */
    TAG_DOUBLE("TAG_DOUBLE", DoubleTag.class),
    /** A array of signed bytes. Length of array is a signed integer. */
    TAG_BYTE_ARRAY("TAG_BYTE_ARRAY", ByteArrayTag.class),
    /** A array of modified UTF-8 string. Length of string is a unsigned short. */
    TAG_STRING("TAG_STRING", StringTag.class),
    /** A list of nameless tags, all the same type. The list starts with {@code tagId} (1 byte), then the length of the list as a signed integer*/
    TAG_LIST("TAG_LIST", (Class<?>) ListTag.class),
    /** A list of named tags. */
    TAG_COMPOUND("TAG_COMPOUND", CompoundTag.class),
    /** A array of signed integers. Length of array is a signed integer. */
    TAG_INT_ARRAY("TAG_INT_ARRAY", IntArrayTag.class),
    /** A array of signed longs. Length of array is a signed integer. */
    TAG_LONG_ARRAY("TAG_LONG_ARRAY", LongArrayTag.class);

    private final Class<?> tagClass;
    private final String typeName;
    private final int id;

    private static final Map<Class<?>, TagType> BY_CLASS = new HashMap<>();
    private static final Map<String, TagType> BY_NAME = new HashMap<>();
    private static final TagType[] BY_ID = values();

    static {
        for (TagType type : TagType.values()) {
            BY_CLASS.put(type.getTagClass(), type);
            BY_NAME.put(type.getTypeName(), type);
        }
    }

    private TagType (String typeName, Class<?> tagClass) {
        this.tagClass = tagClass;
        this.typeName = typeName;
        this.id = values().length;
    }

    public Class<?> getTagClass() {
        return tagClass;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getId() {
        return id;
    }

    public static TagType getByTagClass(Class<?> clazz) {
        TagType ret = BY_CLASS.get(clazz);
        if (ret == null) {
            throw new IllegalArgumentException("Tag type " + clazz + " is unknown!");
        }
        return ret;
    }

    public static TagType getByTypeName(String typeName) {
        TagType type = BY_NAME.get(typeName);
        if (type == null) {
            throw new IllegalArgumentException("Tag type " + typeName + " is unknown!");
        }
        return type;
    }

    public static TagType getById(int id) {
        if (id >= 0 && id < BY_ID.length) {
            TagType tag = BY_ID[id];
            if (tag == null) {
                throw new IllegalArgumentException("Tag type id " + id + " is unknown!");
            }
            return tag;
        } else {
            throw new IndexOutOfBoundsException("Tag type id " + id + " is out of bounds!");
        }
    }
}
