package NBT;

/** Enum of different NBT Tags. */
public enum TagType {
    /** {@code TAG_END} is only used to signify the end of a {@code TAG_COMPOUND}. */
    TAG_END("TAG_END", 0),
    /** A single signed byte. */
    TAG_BYTE("TAG_BYTE", 1),
    /** A 16 bit signed integer. */
    TAG_SHORT("TAG_SHORT", 2),
    /** A 32 bit signed integer. */
    TAG_INT("TAG_INT", 3),
    /** A 64 bit signed integer */
    TAG_LONG("TAG_LONG", 4),
    /** A single-percision floating point number. */
    TAG_FLOAT("TAG_FLOAT", 5),
    /** A double-percision floating point number. */
    TAG_DOUBLE("TAG_DOUBLE", 6),
    /** A array of signed bytes. Length of array is a signed integer. */
    TAG_BYTE_ARRAY("TAG_BYTE_ARRAY", 7),
    /** A array of modified UTF-8 string. Length of string is a unsigned short. */
    TAG_STRING("TAG_STRING", 8),
    /** A list of nameless tags, all the same type. The list starts with {@code tagId} (1 byte), then the length of the list as a signed integer*/
    TAG_LIST("TAG_LIST", 9),
    /** A list of named tags. */
    TAG_COMPOUND("TAG_COMPOUND", 10),
    /** A array of signed integers. Length of array is a signed integer. */
    TAG_INT_ARRAY("TAG_INT_ARRAY", 11),
    /** A array of signed longs. Length of array is a signed integer. */
    TAG_LONG_ARRAY("TAG_LONG_ARRAY", 12);

    private final String tagName;
    private final int tagId;
    private TagType (String tagName, int tagId) {
        this.tagName = tagName;
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public int getTagID() {
        return tagId;
    }
}
