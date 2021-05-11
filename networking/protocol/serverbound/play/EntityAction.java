package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x1C | C->S
public class EntityAction {

    private EntityAction() {}

    public static byte[] execute(int entityID, int actionID, int jumpBoost) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x1C;

        buffer.writeByte(packetID);

        buffer.writeVarInt(entityID);
        buffer.writeVarInt(actionID);

        buffer.writeVarInt(jumpBoost);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Entity ID: {0}, Action ID: {1}, Jump boost: {2}", new Object[]{entityID, actionID, jumpBoost});

        return buffer.getPacket();
    }
    
}