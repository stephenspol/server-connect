package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x0E | C->S
public class InteractEntity {

    private InteractEntity() {}

    public static byte[] execute(int entityID, int type, float x, float y, float z, int hand, boolean sneaking) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x0E;

        buffer.writeByte(packetID);

        buffer.writeVarInt(entityID);
        buffer.writeVarInt(type);

        if (type == 0) {
            buffer.writeFloat(x);
            buffer.writeFloat(y);
            buffer.writeFloat(z);
        }

        if (type == 0 || type == 2) {
            buffer.writeInt(hand);
        }

        buffer.writeBoolean(sneaking);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Entity ID: {0}, Action Type: {1}", new Object[]{entityID, type});

        if (type == 0) log.log(packetInfo, "Target X: {0}, Y: {1}, Z: {2}", new Object[]{x, y, z});

        if (type == 0 || type == 2) log.log(packetInfo, "Hand Used: {0}",  (hand == 0) ? "Main Hand" : "Off Hand");

        log.log(packetInfo, "Player is sneaking: {0}", sneaking);

        return buffer.getPacket();
    }
    
}