package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x1B | S->C
public class Explosion {

    private Explosion() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        float x = buffer.readFloat();
        float y = buffer.readFloat();
        float z = buffer.readFloat();

        float strength = buffer.readFloat();

        log.log(packetInfo, "Explosion at X: {0}, Y: {1}, Z: {2}, Strength: {3}", new Object[]{x, y, z, strength});

        int length = buffer.readInt();

        byte[][] records = new byte[length][3];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < records[i].length; j++) {
                records[i][j] = buffer.readByte();
            }
            log.log(packetInfo, "Record {0} at X: {1}, Y: {2}, Z: {3}", new Object[]{i, records[i][0], records[i][1], records[i][2]});
        }

        float velX = buffer.readFloat();
        float velY = buffer.readFloat();
        float velZ = buffer.readFloat();

        log.log(packetInfo, "Player motion Velocity X: {0}, Y: {1}, Z: {2}", new Object[]{velX, velY, velZ});
    }
    
}