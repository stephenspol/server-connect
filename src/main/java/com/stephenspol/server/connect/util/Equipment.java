package com.stephenspol.server.connect.util;

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
        MAIN_HAND,
        OFF_HAND,
        BOOTS,
        LEGGINGS,
        CHESTPLATE,
        HELMET;

        private final String name;
        
        private static final EquipmentType[] BY_ID = values();

        private EquipmentType() {
            this.name = name().toLowerCase().replace("_", " ");
        }

        public String getName() {
            return name;
        }

        public static EquipmentType getById(int id) {
            if (id >= 0 && id < BY_ID.length) {
                EquipmentType equip = BY_ID[id];
                if (equip == null) {
                    throw new IllegalArgumentException("Equipment id " + id + " is unknown!");
                }
                return equip;
            } else {
                throw new IndexOutOfBoundsException("Equipment id " + id + " is out of bounds!");
            }
        }
    }
    
}
