package util;

public class PlayerProperty {

    private final String name;
    private final String value;

    private final String signature;

    public PlayerProperty(String name, String value) {
        this(name, value, null);
    }

    public PlayerProperty(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    public boolean isSigned() {
        return signature != null;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Value: " + value + (isSigned() ? ", Signature: " + signature : ", Is Signed: false");
    }
    
}
