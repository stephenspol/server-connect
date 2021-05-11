package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x27 | C->S
public class UpdateCommandBlockMinecart {

    private UpdateCommandBlockMinecart() {}

    public static byte[] execute(int entityID, String command, boolean trackOutput) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x27;

        buffer.writeByte(packetID);

        buffer.writeVarInt(entityID);

        buffer.writeString(command);

        buffer.writeBoolean(trackOutput);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Entity ID: {0}, Command: {1}", new Object[]{entityID, command});

        log.log(packetInfo, "Track Output: {0}", trackOutput);

        return buffer.getPacket();
    }
    
}