package networking.stream;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import networking.Server;
import util.Slot;
import util.advancement.Advancement;
import util.advancement.AdvancementDisplay;
import util.recipe.*;
import nbt.*;

public class MinecraftInputBuffer {

    private byte[] buffer;
    private int pos;

    public MinecraftInputBuffer(byte[] buffer) {
        this.buffer = buffer;
        pos = 0;
    }

    public MinecraftInputBuffer(DataInputStream in) throws IOException {
        int i = 0;
	    int j = 0;
	    while (true) {
	        int k = in.read();
            if (k == -1) throw new EOFException("Stream Ended!");
	        i |= (k & 0x7F) << j++ * 7;
	        if (j > 5) throw new IOException("VarInt too big!");
	        if ((k & 0x80) != 128) break;
	    }

        if (i < 0) throw new IOException("Premature stream. Size is: " + i);
	    
        buffer = new byte[i];

        in.readFully(buffer);

        pos = 0;
    }

    public final byte readByte() {
        return buffer[pos++];
    }

    public final short readUnsignedByte() {
        return (short) (buffer[pos++] & 0xFF);
    }

    public final boolean readBoolean() {
        return readByte() != 0;
    }

    public final byte[] readBytes(int count) {
        byte[] b = new byte[count];
        System.arraycopy(buffer, pos, b, 0, count);
        pos += count;
        
        return b;
    }

    public final short readShort() {
        return (short) (((readUnsignedByte()) << 8) | (readUnsignedByte()));
    }

    public final int readUnsignedShort() {
        return ((readUnsignedByte()) << 8) | (readUnsignedByte());
    }

    public final int readInt() {
        return ((readUnsignedByte() << 24) | (readUnsignedByte() << 16) | (readUnsignedByte() << 8) | (readUnsignedByte()));
    }

    public final float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public final double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public final long readLong() {
        return (((long)readUnsignedByte() << 56) +
                ((long)(readUnsignedByte() & 255) << 48) +
                ((long)(readUnsignedByte() & 255) << 40) +
                ((long)(readUnsignedByte() & 255) << 32) +
                ((long)(readUnsignedByte() & 255) << 24) +
                ((readUnsignedByte() & 255) << 16) +
                ((readUnsignedByte() & 255) <<  8) +
                ((readUnsignedByte() & 255) <<  0));
    }
    
    public final String readString() throws IOException {
		return readString(readVarInt());
	}

    public final String readString(int length) throws IOException {
		if (length == -1) {
			throw new IOException(Server.PREMATURE);
		}

		else if (length == 0) {
			return "";
		}

		return new String(readBytes(length));
	}

    public final UUID readUUID() {
        long msb = readLong();
        long lsb = readLong();

        return new UUID(msb, lsb);
    }

	public final int readVarInt() throws IOException {
	    int i = 0;
	    int j = 0;
	    while (true) {
	        byte k = readByte();
	        i |= (k & 0x7F) << j++ * 7;
	        if (j > 5) throw new IOException("VarInt too big");
	        if ((k & 0x80) != 128) break;
	    }
	    return i;
	}

    public final long readVarLong() throws IOException {
        int numRead = 0;
        long result = 0;
        while (true) {
            byte read = readByte();
            long value = (read & 0b01111111);
            result |= (value << (7 * numRead));
    
            numRead++;
            if (numRead > 10) throw new IOException("VarLong is too big");
            if ((read & 0b10000000) != 0) break;
        }
    
        return result;
    }
	
	public final int[] readPos() {
		long val = readLong();
		
		int x = (int)(val >> 38); // 26 MSBs
		int y = (int)((val >> 26) & 0xFFF); // 12 bits between them
		int z = (int)(val << 38 >> 38); // 26 LSBs
		
		if (x >= Math.pow(2, 25)) x -= Math.pow(2, 26);
		if (y >= Math.pow(2, 11)) y -= Math.pow(2, 12);
		if (z >= Math.pow(2, 25)) z -= Math.pow(2, 26);
		
		return new int[]{x, y, z};
	}

    public short readAngle() {
        return (short) (readByte() * (360F/256F));
    }

    public double readFixedPointNumberInt() {
        return readInt() / 32.0D;
    }


	public final Tag readNBT() throws IOException {
		return readTag(0);
	}

	private final Tag<?> readTag(int depth) throws IOException {
        int id = readUnsignedByte();

		TagType type = TagType.getById(id);

		String name;
        
        if (type != TagType.TAG_END) {

			name = readString(readUnsignedShort());

		} else {
			name = "";
		}

        return readTagPayload(type, name, depth);
    }

	private final Tag<?> readTagPayload(TagType type, String name, int depth) throws IOException {
		switch (type) {
            case TAG_END:
                return new EndTag();

            case TAG_BYTE:
                return new ByteTag(name, readByte());

            case TAG_SHORT:
                return new ShortTag(name, readShort());

            case TAG_INT:
                return new IntTag(name, readInt());

            case TAG_LONG:
                return new LongTag(name, readLong());

            case TAG_FLOAT:
                return new FloatTag(name, readFloat());

            case TAG_DOUBLE:
                return new DoubleTag(name, readDouble());

            case TAG_BYTE_ARRAY:
                int length = readInt();
                return new ByteArrayTag(name, readBytes(length));

            case TAG_STRING:
                return new StringTag(name, readString(readUnsignedShort()));

            case TAG_LIST:
                TagType childType = TagType.getById(readByte());
                length = readInt();

                Class<?> cls = childType.getTagClass();
                List<Tag<?>> tagList = new ArrayList<>(length);
                for (int i = 0; i < length; i++) {
                    Tag<?> tag = readTagPayload(childType, "", depth + 1);
                    if (tag instanceof EndTag) {
                        throw new IOException("TAG_End not permitted in a list.");
                    } else if (!cls.isInstance(tag)) {
                        throw new IOException("Mixed tag types within a list.");
                    }
                    tagList.add(tag);
                }

                return new ListTag(name, cls, tagList);

            case TAG_COMPOUND:
				Map<String, Tag<?>> compoundTagList = new HashMap<>();
                while (true) {
                    Tag<?> tag = readTag(depth + 1);
                    if (tag instanceof EndTag) {
                        break;
                    } else {
                        compoundTagList.put(name, tag);
                    }
                }

                return new CompoundTag(name, compoundTagList);

            case TAG_INT_ARRAY:
                length = readInt();
                int[] ints = new int[length];
                for (int i = 0; i < length; i++) {
                    ints[i] = readInt();
                }
                return new IntArrayTag(name, ints);

            case TAG_LONG_ARRAY:
                length = readInt();
                long[] longs = new long[length];
                for (int i = 0; i < length; i++) {
                    longs[i] = readLong();
                }
                return new LongArrayTag(name, longs);

            default:
                throw new IOException("Invalid tag type: " + type + ".");
        }
	}

    public final Slot readSlot() throws IOException {
        boolean present = readBoolean();

        if (present) {
            int id = readVarInt();
            byte count = readByte();
            Tag<?> data = readNBT();

            return new Slot(id, count, data);
        }

        return null;
    }

    public final Recipe readRecipe() throws IOException {
        String type = readString();
        String id = readString();

        RecipeType recipe = RecipeType.getByName(type);

        switch (recipe) {
            case CRAFTING_SHAPELESS:
                String group = readString();

                int length = readVarInt();

                Ingredient[] ingredients = new Ingredient[length];

                for (int i = 0; i < length; i++) {
                    ingredients[i] = readIngredient();
                }
                
                Slot result = readSlot();

                return new CraftingShapeless(type, id, group, ingredients, result);

            case CRAFTING_SHAPED:
                int width = readVarInt();
                int height = readVarInt();

                group = readString();

                length = width * height;

                ingredients = new Ingredient[length];

                for (int i = 0; i < length; i++) {
                    ingredients[i] = readIngredient();
                }
                
                result = readSlot();

                return new CraftingShaped(type, id, group, ingredients, result);
            
            case CRAFTING_SPECIAL_ARMORDYE:
                return new CraftingSpecialArmorDye(type, id);

            case CRAFTING_SPECIAL_BOOKCLONING:
                return new CraftingSpecialBookCloning(type, id);

            case CRAFTING_SPECIAL_MAPCLONING:
                return new CraftingSpecialMapCloning(type, id);

            case CRAFTING_SPECIAL_MAPEXTENDING:
                return new CraftingSpecialMapExtending(type, id);

            case CRAFTING_SPECIAL_FIREWORK_ROCKET:
                return new CraftingSpecialFireworkRocket(type, id);
            
            case CRAFTING_SPECIAL_FIREWORK_STAR:
                return new CraftingSpecialFireworkStar(type, id);

            case CRAFTING_SPECIAL_FIREWORK_STAR_FADE:
                return new CraftingSpecialFireworkStarFade(type, id);

            case CRAFTING_SPECIAL_REPAIRITEM:
                return new CraftingSpecialRepairItem(type, id);

            case CRAFTING_SPECIAL_TIPPEDARROW:
                return new CraftingSpecialTippedArrow(type, id);

            case CRAFTING_SPECIAL_BANNERDUPLICATE:
                return new CraftingSpecialBannerDuplicate(type, id);

            case CRAFTING_SPECIAL_BANNERADDPATTERN:
                return new CraftingSpecialBannerAddPattern(type, id);

            case CRAFTING_SPECIAL_SHIELDDECORATION:
                return new CraftingSpecialShieldDecoration(type, id);

            case CRAFTING_SPECIAL_SHULKERBOXCOLORING:
                return new CraftingSpecialShulkerBoxColoring(type, id);

            case CRAFTING_SPECIAL_SUSPICIOUSSTEW:
                return new CraftingSpecialSuspiciousStew(type, id);

            case SMELTING:
                group = readString();

                Ingredient ingredient = readIngredient();

                result = readSlot();

                float xp = readFloat();

                int cookingTime = readVarInt();

                return new CraftingSmelting(type, id, group, ingredient, result, xp, cookingTime);

            case BLASTING:
                group = readString();

                ingredient = readIngredient();

                result = readSlot();

                xp = readFloat();

                cookingTime = readVarInt();

                return new CraftingBlasting(type, id, group, ingredient, result, xp, cookingTime);

            case SMOKING:
                group = readString();

                ingredient = readIngredient();

                result = readSlot();

                xp = readFloat();

                cookingTime = readVarInt();

                return new CraftingSmoking(type, id, group, ingredient, result, xp, cookingTime);

            case CAMPFIRE_COOKING:
                group = readString();

                ingredient = readIngredient();

                result = readSlot();

                xp = readFloat();

                cookingTime = readVarInt();

                return new CraftingCampfireCooking(type, id, group, ingredient, result, xp, cookingTime);

            case STONECUTTING:
                group = readString();

                ingredient = readIngredient();

                result = readSlot();

                return new CraftingStonecutting(type, id, group, ingredient, result);

            case SMITHING:
                Ingredient base = readIngredient();

                Ingredient addition = readIngredient();

                result = readSlot();

                return new CraftingSmithing(type, id, base, addition, result);

            default:
                throw new IOException("Invalid recipe: " + recipe.getRecipeName() + ".");
        }
    }

    private final Ingredient readIngredient() throws IOException {
        Slot[] slots = new Slot[readVarInt()];

        for (int i = 0; i < slots.length; i++) {
            slots[i] = readSlot();
        }

        return new Ingredient(slots);
    }

    public final Advancement readAdvancement() throws IOException {
        boolean hasParent = readBoolean();

        String parentID = null;

        if (hasParent) {
            parentID = readString();
        }

        boolean hasDisplay = readBoolean();

        AdvancementDisplay display = null;

        if (hasDisplay) {
            display = readAdvancementDisplay();
        }

        int length = readVarInt();

        String[] criterias = new String[length];

        for (int i = 0; i < length; i++) {
            criterias[i] =  readString();
        }

        length = readVarInt();

        int length2 = readVarInt();

        String[][] requierments = new String[length][length2];

        for (int i = 0; i < length2; i++) {
            for (int j = 0; j < length; j++) {
                requierments[i][j] = readString();
            }
        }

        return new Advancement(parentID, display, criterias, requierments);
    }

    private final AdvancementDisplay readAdvancementDisplay() throws IOException {
        String title = readString();
        String description = readString();

        Slot icon = readSlot();

        int frameTypeEnum = readVarInt();

        String frameType;

        if (frameTypeEnum == 0) frameType = "task";       
        else if (frameTypeEnum == 1) frameType = "challenge";
        else if (frameTypeEnum == 2) frameType = "goal";
        else throw new IOException("Frame Type " + frameTypeEnum + " is no a valid type!");

        byte flags = (byte) readInt();
        String backgroundTexture = null;

        if (Server.getBit(flags, 0)) backgroundTexture = readString();

        float x = readFloat();
        float y = readFloat();

        return new AdvancementDisplay(title, description, icon, frameType, flags, backgroundTexture, x, y);
    }

    public final Map<String, Long> readAdvancementProgress() throws IOException {
        int length = readVarInt();

        Map<String, Long> advancementProgress = new HashMap<>();

        for (int i = 0; i < length; i++) {
            String name = readString();

            boolean achieved = readBoolean();

            Long date = null;

            if (achieved) {
                date = readLong();
            }

            advancementProgress.put(name, date);
        }

        return advancementProgress;
    }

    public final <T> T read(Class<T> cls) throws IOException {
        return read(cls, null);
    }

    public final <T> T read(Class<T> cls, String type) throws IOException {

        if (type == null) {
            if (cls.isAssignableFrom(boolean.class))          return cls.cast(readBoolean());   
        
            else if (cls.isAssignableFrom(byte.class))        return cls.cast(readByte());

            else if (cls.isAssignableFrom(short.class))       return cls.cast(readShort());
            
            else if (cls.isAssignableFrom(int.class))         return cls.cast(readInt());

            else if (cls.isAssignableFrom(float.class))       return cls.cast(readFloat());

            else if (cls.isAssignableFrom(double.class))      return cls.cast(readDouble());

            else if (cls.isAssignableFrom(long.class))        return cls.cast(readLong());

            else if (cls.isAssignableFrom(String.class))      return cls.cast(readString());

            else if (cls.isAssignableFrom(UUID.class))        return cls.cast(readUUID());

            else if (cls.isAssignableFrom(Tag.class))         return cls.cast(readNBT());

            else if (cls.isAssignableFrom(Slot.class))        return cls.cast(readSlot());

            else if (cls.isAssignableFrom(Recipe.class))      return cls.cast(readRecipe());

            else if (cls.isAssignableFrom(Advancement.class)) return cls.cast(readAdvancement());
        } else {
            if (cls.isAssignableFrom(short.class) && type.equals("angle"))       return cls.cast(readAngle());

            else if (cls.isAssignableFrom(int.class) && type.equals("var"))      return cls.cast(readVarInt());

            else if (cls.isAssignableFrom(int[].class) && type.equals("pos"))    return cls.cast(readPos());

            else if (cls.isAssignableFrom(double.class) && type.equals("fixed")) return cls.cast(readFixedPointNumberInt());

            else if (cls.isAssignableFrom(long.class) && type.equals("var"))     return cls.cast(readVarLong());
        }
        
        throw new IOException("Class " + cls.getName() + ((type != null) ? " and type " + type : "") + " is not supported");
    }

    public final <T> T[] readArray(Class<T> cls) throws IOException {
        return readArray(cls, null);
    }

    public final <T> T[] readArray(Class<T> cls, int length) throws IOException {
        return readArray(cls, null, length);
    }

    public final <T> T[] readArray(Class<T> cls, String type) throws IOException {
        return readArray(cls, type, readVarInt());
    }

    // Java does not support Autoboxing arrays which can cause problems
    @SuppressWarnings("unchecked")
    public final <T> T[] readArray(Class<T> cls, String type, int length) throws IOException {
        T[] genericArray = (T[]) Array.newInstance(cls, length);

        for (int i = 0; i < length; i++) {
            Array.set(genericArray, i, read(cls, type));
            //genericArray[i] = read(cls, type);
        }

        return genericArray;
    }

    public int size() {
        return buffer.length;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public byte[] readFully() {
        int remainingSize = (size() - 1) - pos;
        
        return readBytes(remainingSize);
    }
}
