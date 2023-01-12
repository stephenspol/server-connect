package com.stephenspol.server.connect.nbt;

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
    TAG_LIST("TAG_LIST", ListTag.class),
    /** A list of named tags. */
    TAG_COMPOUND("TAG_COMPOUND", CompoundTag.class),
    /** A array of signed integers. Length of array is a signed integer. */
    TAG_INT_ARRAY("TAG_INT_ARRAY", IntArrayTag.class),
    /** A array of signed longs. Length of array is a signed integer. */
    TAG_LONG_ARRAY("TAG_LONG_ARRAY", LongArrayTag.class);

    private final Class<?> tagClass;
    private final String typeName;

    private static final String NOT_FOUND = " is not found!";

    private static final Map<Class<?>, TagType> BY_CLASS = new HashMap<>();
    private static final Map<String, TagType> BY_NAME = new HashMap<>();
    private static final TagType[] BY_ID = values();

    static {
        for (TagType type : BY_ID) {
            BY_CLASS.put(type.getTagClass(), type);
            BY_NAME.put(type.getTypeName(), type);
        }
    }

    private TagType (String typeName, Class<?> tagClass) {
        this.tagClass = tagClass;
        this.typeName = typeName;
    }

    public Class<?> getTagClass() {
        return tagClass;
    }

    public String getTypeName() {
        return typeName;
    }

    public static TagType getByTagClass(Class<?> cls) {
        TagType ret = BY_CLASS.get(cls);
        if (ret == null) {
            throw new IllegalArgumentException("Tag type " + cls + NOT_FOUND);
        }
        return ret;
    }

    public static TagType getByTypeName(String typeName) {
        TagType type = BY_NAME.get(typeName);
        if (type == null) {
            throw new IllegalArgumentException("Tag type " + typeName + NOT_FOUND);
        }
        return type;
    }

    public static TagType getById(int id) {
        if (id >= 0 && id < BY_ID.length) {
            TagType tag = BY_ID[id];
            if (tag == null) {
                throw new IllegalArgumentException("Tag type id " + id + NOT_FOUND);
            }
            return tag;
        } else {
            throw new IndexOutOfBoundsException("Tag type id " + id + " is out of bounds!");
        }
    }

    public static int getIdByTagType(TagType tag) {
        for (int i = 0; i < BY_ID.length; i++) {
            if (tag.name().equals(BY_ID[i].name())) {
                return i;
            }
        }

        throw new IllegalArgumentException("Tag Type " + tag.name() + " does not exist!");
    }
}
