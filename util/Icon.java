package util;

public class Icon {

    private int type;

    private byte x;
    private byte z;
    private byte dir;

    private final String name;

    public Icon(int type, byte x, byte z, byte dir) {
        this(type, x, z, dir, null);
    }
    
    public Icon(int type, byte x, byte z, byte dir, String name) {
        this.type = type;
        
        this.x = x;
        this.z = z;
        this.dir = dir;

        this.name = name;
    }

    public int getType() {
        return type;
    }

    public byte[] getCordinates() {
        return new byte[]{x, z};
    }

    public byte getDir() {
        return dir;
    }

    public String getName() {
        return name;
    }

    public boolean hasName() {
        return name != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Type: " + type + ", Cords:[X: " + x + ", Z: " + z + "], Direction: " + dir);

        sb.append("\n" + (hasName()? "Name: " + name : "Has Name: false"));

        return sb.toString();
    }

}
