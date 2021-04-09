package util;

public class Equipment {

    private byte slot;
    private Slot item;

    public Equipment(byte slot, Slot item) {
        this.slot = slot;
        this.item = item;
    }

    public byte getSlotID() {
        return slot;
    }

    public Slot getItem() {
        return item;
    }

    public EquipmentType getSlot() {
        return EquipmentType.getById(slot);
    }

    @Override
    public String toString() {
        return "Slot: " + getSlot().getName() + ", Item: " + item;
    }

    enum EquipmentType {
        MAIN_HAND(0),
        OFF_HAND(1),
        BOOTS(2),
        LEGGINGS(3),
        CHESTPLATE(4),
        HELMET(5);

        private final String name;
        private final int id;

        private static final EquipmentType[] BY_ID;

        static {
            BY_ID = new EquipmentType[6];
            for (EquipmentType e : EquipmentType.values()) {
                BY_ID[e.getId()] = e;
            }
        }

        private EquipmentType(int id) {
            this.id = id;
            this.name = name().toLowerCase().replace("_", " ");
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public static EquipmentType getById(int id) {
            if (id >= 0 && id < BY_ID.length) {
                EquipmentType equipId = BY_ID[id];
                if (equipId == null) {
                    throw new IllegalArgumentException("Equipment id " + id + " is unknown!");
                }
                return equipId;
            } else {
                throw new IndexOutOfBoundsException("Equipment id " + id + " is out of bounds!");
            }
        }
    }
    
}
