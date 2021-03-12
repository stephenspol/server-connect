package NBT;

public class EndTag extends Tag<Object> {

    public EndTag() {
        super(TagType.TAG_END);
    }
    
    public Object getValue() {
        return null;
    }
}