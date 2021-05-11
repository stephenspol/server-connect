package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x14 | C->S
public class PlayerRot {

    private PlayerRot() {}

    public static byte[] execute(float yaw, float pitch, boolean onGround) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x14;

        buffer.writeByte(packetID);

        buffer.writeFloat(yaw);
        buffer.writeFloat(pitch);

        buffer.writeBoolean(onGround);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Yaw: {0}, Pitch: {1}, On Ground: {2}", new Object[]{yaw, pitch, onGround});

        return buffer.getPacket();
    }
    
}