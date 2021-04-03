package networking.stream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import networking.Server;
import util.Slot;
import util.recipe.*;
import NBT.*;

public class MinecraftInputStream extends DataInputStream {

    public MinecraftInputStream(InputStream in) {
        super(in);
    }
    
    public final String readString() throws IOException {
		int length = readVarInt();

		if (length == -1) {
			throw new IOException(Server.PREMATURE);
		}

		else if (length == 0) {
			throw new IOException(Server.INVALID_LENGTH);
		}

		byte[] buffer = new byte[length];
		readFully(buffer);  //read json string

		return new String(buffer);
	}

    public final UUID readUUID() throws IOException {
        long msb = readLong();
        long lsb = readLong();

        return new UUID(msb, lsb);
    }

	public final int readVarInt() throws IOException {
	    int i = 0;
	    int j = 0;
	    while (true) {
	        int k = readByte();
	        i |= (k & 0x7F) << j++ * 7;
	        if (j > 5) throw new IOException("VarInt too big");
	        if ((k & 0x80) != 128) break;
	    }
	    return i;
	}
	
	public final int[] readPos() throws IOException {
		long val = readLong();
		
		int x = (int)(val >> 38); // 26 MSBs
		int y = (int)((val >> 26) & 0xFFF); // 12 bits between them
		int z = (int)(val << 38 >> 38); // 26 LSBs
		
		if (x >= Math.pow(2, 25)) x -= Math.pow(2, 26);
		if (y >= Math.pow(2, 11)) y -= Math.pow(2, 12);
		if (z >= Math.pow(2, 25)) z -= Math.pow(2, 26);
		
		return new int[]{x, y, z};
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
                byte[] bytes = new byte[length];
                readFully(bytes);
                return new ByteArrayTag(name, bytes);

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

		byte[] input = new byte[length];
		readFully(input);  //read json string

		return new String(input);
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
    
}
