package networking.buffer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nbt.CompoundTag;
import nbt.ListTag;
import nbt.Tag;
import nbt.TagType;
import util.Slot;

public class MinecraftOutputBuffer {

	private final ArrayList<Byte> buffer;
    
    public MinecraftOutputBuffer() {
        buffer = new ArrayList<>();
    }

	public final void writeBytes(byte[] bytes) {
		for (Byte b : bytes) {
			buffer.add(b);
		}
	}

	public final byte[] getBytes() {
		byte[] bytes = new byte[buffer.size()];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = buffer.get(i);
		}

		buffer.clear();

		return bytes;
	}

	public final byte[] getPacket() {
		buffer.add(0, (byte) size());

		return getBytes();
	}

	public final void writeBoolean(boolean bool) {
		buffer.add(bool ? (byte) 1 : 0);
	}

	public final void writeByte(byte b) {
		buffer.add(b);
	}

	public final void writeByte(int i) {
		buffer.add((byte) (i & 0xFF));
	}

	public final void writeByte(long l) {
		buffer.add((byte) (l & 0xFF));
	}

	public final void writeShort(short value) {
		writeByte(value >>> 8);
        writeByte(value);
	}

	public final void writeShort(int value) {
		writeShort((short) value);
	}

	public final void writeInt(int value) {
		writeByte(value >>> 24);
        writeByte(value >>> 16);
        writeByte(value >>> 8);
        writeByte(value);

	}

	public final void writeLong(long value) {
		writeInt((int) (value >> 32));
        writeInt((int) (value));
	}

	public final void writeFloat(float value) {
		writeInt(Float.floatToIntBits(value));
	}

	public final void writeDouble(double value) {
		writeLong(Double.doubleToLongBits(value));
	}

	public final void writeString(String string) {
		writeString(string, StandardCharsets.UTF_8);
	}

    public final void writeString(String string, Charset charset) {
	    byte[] bytes = string.getBytes(charset);
	    writeVarInt(bytes.length);
	    writeBytes(bytes);
	}

	private final void writeTagString(String string) {
		byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
		writeShort(bytes.length);
		writeBytes(bytes);
	}

    public final void writeVarInt(int value) {
	    while (true) {
	        if ((value & 0xFFFFFF80) == 0) {
	          writeByte(value);
	          return;
	        }

	        writeByte(value & 0x7F | 0x80);
	        value >>>= 7;
	    }
	}

	public final void writeVarLong(long value) {
	    while (true) {
	        if ((value & 0xFFFFFF80) == 0) {
	          writeByte(value);
	          return;
	        }

	        writeByte(value & 0x7F | 0x80);
	        value >>>= 7;
	    }
	}

	public final void writeUUID(UUID uuid) {
		writeLong(uuid.getMostSignificantBits());
		writeLong(uuid.getLeastSignificantBits());
	}

    public final void writePos(int[] pos) {
		if (pos.length != 3) {
			throw new IllegalArgumentException("Position array is not the length of 3!");
		}

		writeLong(((pos[0] & 0x3FFFFFF) << 38) | ((pos[1] & 0xFFF) << 26) | (pos[2] & 0x3FFFFFF));
	}

	public final void writeNBT(Tag<?> tag) {
		writeByte(tag.getTagType().getId());

		if (tag.getTagType() != TagType.TAG_END) {
			writeTagString(tag.getName());
		}

		writeTagPayload(tag);
	}

	private final void writeTagPayload(Tag<?> tag) {
		switch (tag.getTagType()) {
			case TAG_END:
				return;

			case TAG_BYTE:
				writeByte((byte) tag.getValue());
				break;
				
			case TAG_SHORT:
				writeShort((short) tag.getValue());
				break;

			case TAG_INT:
				writeInt((int) tag.getValue());
				break;

			case TAG_LONG:
				writeLong((long) tag.getValue());
				break;

			case TAG_FLOAT:
				writeFloat((float) tag.getValue());
				break;

			case TAG_DOUBLE:
				writeDouble((double) tag.getValue());
				break;

			case TAG_BYTE_ARRAY:
				byte[] bytes = (byte[]) tag.getValue();
				writeInt(bytes.length);
				writeBytes(bytes);
				break;

			case TAG_STRING:
				writeTagString((String) tag.getValue());
				break;

			case TAG_LIST:
				ListTag<?> listTag = (ListTag<?>) tag;
				Class<?> cls = listTag.getElementType();

				List<Tag<?>> tags = (List<Tag<?>>) listTag.getValue();
				
				writeByte(TagType.getByTagClass(cls).getId());
				writeInt(tags.size());

				for (Tag<?> t : tags) {
					writeTagPayload(t);
				}
				break;

			case TAG_COMPOUND:
				CompoundTag compoundTag = (CompoundTag) tag;
				
				for (Tag<?> childTag : compoundTag.getValue().values()) {
					writeNBT(childTag);
				}

				writeByte(TagType.TAG_END.getId());
				break;

			case TAG_INT_ARRAY:
				int[] ints = (int[]) tag.getValue();
				writeInt(ints.length);

				for (int i : ints) {
					writeInt(i);
				}
				break;
				
			case TAG_LONG_ARRAY:
				long[] longs = (long[]) tag.getValue();
				writeInt(longs.length);

				for (long l : longs) {
					writeLong(l);
				}
				break;

			default:
				throw new IllegalArgumentException("Invalid tag type " + tag.getTagType() + ".");
		}
	}

	public final void writeSlot(Slot slot) {
		boolean present = (slot != null);

		writeBoolean(present);

		if (present) {
			writeVarInt(slot.getItemId());
			writeByte(slot.getCount());
			writeNBT(slot.getData());
		}
	}

	public int size() {
		return buffer.size();
	}
}
