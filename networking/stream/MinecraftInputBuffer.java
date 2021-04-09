package networking.stream;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import networking.Server;
import util.Slot;
import util.recipe.*;
import NBT.*;

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
	        i |= (k & 0x7F) << j++ * 7;
	        if (j > 5) throw new IOException("VarInt too big");
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
		int length = readVarInt();

		if (length == -1) {
			throw new IOException(Server.PREMATURE);
		}

		else if (length == 0) {
			throw new IOException(Server.INVALID_LENGTH);
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

			name = readTagString();

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
                return new StringTag(name, readTagString());

            case TAG_LIST:
                TagType childType = TagType.getById(readByte());
                length = readInt();

                Class<?> clazz = childType.getTagClass();
                List<Tag<?>> tagList = new ArrayList<>(length);
                for (int i = 0; i < length; i++) {
                    Tag<?> tag = readTagPayload(childType, "", depth + 1);
                    if (tag instanceof EndTag) {
                        throw new IOException("TAG_End not permitted in a list.");
                    } else if (!clazz.isInstance(tag)) {
                        throw new IOException("Mixed tag types within a list.");
                    }
                    tagList.add(tag);
                }

                return new ListTag(name, clazz, tagList);

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
                    longs[i] = readShort();
                }
                return new LongArrayTag(name, longs);

            default:
                throw new IOException("Invalid tag type: " + type + ".");
        }
	}

	private final String readTagString() throws IOException {
		int length = readUnsignedShort();

		if (length == -1) {
			throw new IOException(Server.PREMATURE);
		}
		
		else if (length == 0) {
			return "";
		}

		return new String(readBytes(length));
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

            case CRAFTING_SPECIAL_BANNERDUPLICATION:
                return new CraftingSpecialBannerDuplication(type, id);

            case CRAFTING_SPECIAL_BANNERADDPATTERN:
                return new CraftingSpecialBannerAddPattern(type, id);

            case CRAFTING_SPECIAL_SHEILDDECORATION:
                return new CraftingSpecialShieldDecoration(type, id);

            case CRAFTING_SPECIAL_SHULKERBOXCOLORING:
                return new CraftingSpecialShulkerBoxColoring(type, id);

            case CRAFTING_SPECIAL_SUSPECIOUSSTEW:
                return new CraftingSpecialSuspeciousStew(type, id);

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

    public int size() {
        return buffer.length;
    }
}
