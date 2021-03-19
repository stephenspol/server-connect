package networking.stream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import networking.Server;

import NBT.*;

public class MinecraftInputStream extends DataInputStream {

    public MinecraftInputStream(InputStream in) {
        super(in);
    }
    
    public String readString() throws IOException {
		int length = readVarInt();

		if (length == -1) {
			throw new IOException(Server.PREMATURE);
		}

		else if (length == 0) {
			throw new IOException(Server.INVALID_LENGTH);
		}

		byte[] input = new byte[length];
		readFully(input);  //read json string

		return new String(input);
	}

	public int readVarInt() throws IOException {
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
	
	public int[] readPos() throws IOException {
		long val = readLong();
		
		int x = (int)(val >> 38); // 26 MSBs
		int y = (int)((val >> 26) & 0xFFF); // 12 bits between them
		int z = (int)(val << 38 >> 38); // 26 LSBs
		
		if (x >= Math.pow(2, 25)) x -= Math.pow(2, 26);
		if (y >= Math.pow(2, 11)) y -= Math.pow(2, 12);
		if (z >= Math.pow(2, 25)) z -= Math.pow(2, 26);
		
		return new int[]{x, y, z};
	}

	public Tag readNBT() throws IOException {
		return readTag(0);
	}

	private Tag<?> readTag(int depth) throws IOException {
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

	private Tag<?> readTagPayload(TagType type, String name, int depth) throws IOException {
		switch (type) {
            case TAG_END:
                if (depth == 0) {
                    throw new IOException("TAG_End found without a TAG_Compound/TAG_List tag preceding it.");
                } else {
                    return new EndTag();
                }

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

	private String readTagString() throws IOException {
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

    
}
