package util.advancement;

import java.util.Arrays;

public class Advancement {

    private String parentID;

    private AdvancementDisplay display;

    private String[] criterias;
    private String[][] requirements;

    public Advancement(String[] criterias, String[][] requirements) {
        this(null, null, criterias, requirements);
    }

    public Advancement(String parentID, AdvancementDisplay display, String[] criterias, String[][] requirements) {
        this.parentID = parentID;
        this.display = display;

        this.criterias = criterias;
        this.requirements = requirements;
    }
    
    public String getParentID() {
        return parentID;
    }

    public boolean hasParent() {
        return parentID != null;
    }

    public AdvancementDisplay getDisplay() {
        return display;
    }

    public boolean hasDisplay() {
        return display != null;
    }

    public String[] getCriterias() {
        return criterias;
    }

    public String[][] getRequierments() {
        return requirements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append((hasParent() ? "Parent ID: " + parentID : "Has no parent") + ", ");
        sb.append((hasDisplay() ? "Display: " + display : "Has no display") + "\n");

        sb.append("Critierias: " + Arrays.toString(criterias));

        sb.append("Requirements: " + Arrays.toString(requirements));

        return sb.toString();
    }
}
