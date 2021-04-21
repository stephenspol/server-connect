package util.advancement;

import util.Slot;

public class AdvancementDisplay {

    private String title;
    private String description;

    private Slot icon;

    private String frameType;

    private int flags;

    private String backgroundTexture;

    private float x;
    private float y;

    public AdvancementDisplay(String title, String description, Slot icon, String frameType, int flags, float x, float y) {
        this(title, description, icon, frameType, flags, null, x, y);
    }

    public AdvancementDisplay(String title, String description, Slot icon, String frameType, int flags, String backgroundTexture, float x, float y) {
        this.title = title;
        this.description = description;

        this.icon = icon;

        this.frameType = frameType;
        
        this.flags = flags;

        this.backgroundTexture = backgroundTexture;

        this.x = x;
        this.y = y;
    }

    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }

    public Slot getIcon() {
        return icon;
    }

    public String getFrameType() {
        return frameType;
    }

    public int getFlags() {
        return flags;
    }

    public String getBackgroundTexture() {
        return backgroundTexture;
    }

    public boolean hasBackgroundTexture() {
        return backgroundTexture != null;
    }

    public float[] getPosition() {
        return new float[]{x, y};
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Title: " + title + ", Description: " + description + "\n");
        sb.append("Icon: " + icon + "\n");
        sb.append("Frame Type: " + frameType + ", Flags: " + flags);
        sb.append((hasBackgroundTexture()) ? ", Background Texture: " + backgroundTexture : ", No Background Texture" + "\n");
        sb.append("Position, X: " + x + ", Y: " + y);

        return sb.toString();
    }
}
