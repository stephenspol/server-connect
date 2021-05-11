package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x2A | C->S
public class UpdateStructureBlock {

    private UpdateStructureBlock() {}

    public static byte[] execute(int[] pos, int action, int mode, String name, byte offsetX, byte offsetY, byte offsetZ,
                                 byte sizeX, byte sizeY, byte sizeZ, int mirror, int rotation, String metadata, float integrity,
                                 long seed, byte flags) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x2A;

        buffer.writeByte(packetID);

        buffer.writePos(pos);

        buffer.writeVarInt(action);
        buffer.writeVarInt(mode);

        buffer.writeString(name);

        buffer.writeByte(offsetX);
        buffer.writeByte(offsetY);
        buffer.writeByte(offsetZ);

        buffer.writeByte(sizeX);
        buffer.writeByte(sizeY);
        buffer.writeByte(sizeZ);

        buffer.writeVarInt(mirror);
        buffer.writeVarInt(rotation);

        buffer.writeString(metadata);

        buffer.writeFloat(integrity);

        buffer.writeVarLong(seed);

        buffer.writeByte(flags);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});

        log.log(packetInfo, "Action: {0}, Mode: {1}, Name: {2}", new Object[]{action, mode, name});

        log.log(packetInfo, "Offset X: {0}, Offset Y: {1}, Offset Z: {2}", new Object[]{offsetX, offsetY, offsetZ});
        log.log(packetInfo, "Size X: {0}, Size Y: {1}, Size Z: {2}", new Object[]{sizeX, sizeY, sizeZ});

        log.log(packetInfo, "Mirror: {0}, Rotation: {1}, Metatdata: {2}", new Object[]{mirror, rotation, metadata});

        log.log(packetInfo, "Intergity: {0}, Seed: {1}, Flags: {2}", new Object[]{integrity, seed, flags});

        return buffer.getPacket();
    }
    
}