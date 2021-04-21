package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;
import util.ParticleType;
import util.Slot;

// Packet ID 0x22 | S->C
public class Particle {

    private Particle() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int id = buffer.readInt();

        boolean longDist = buffer.readBoolean();

        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        float offsetX = buffer.readFloat();
        float offsetY = buffer.readFloat();
        float offsetZ = buffer.readFloat();

        float data = buffer.readFloat();
        int count = buffer.readInt();

        log.log(packetInfo, "Particle ID: {0}, Name: {1}, Is it Long Distance: {2}", new Object[]{id, ParticleType.getById(id), longDist});
        log.log(packetInfo, "Position, X: {0}, Y: {1}, Z: {2}", new Object[]{x, y, z});
        log.log(packetInfo, "Offset Postion: X: {0}, Y: {1}, Z: {2}", new Object[]{offsetX, offsetY, offsetZ});
        log.log(packetInfo, "Data: {0}, Count: {1}", new Object[]{data, count});

        if (id >= 0 && id <= 61) {
            if (id == 3 || id == 23) {
                int blockState = buffer.readVarInt();
                log.log(packetInfo, "Block State is {0}", blockState);

            } else if (id == 14) {
                float red = buffer.readFloat();
                float green = buffer.readFloat();
                float blue = buffer.readFloat();
                
                float scale = buffer.readFloat();

                log.log(packetInfo, "Red: {0}, Green: {1}, Blue: {2}, Scale: {3}", new Object[]{red, green, blue, scale});
            
            } else if (id == 32) {
                Slot item = buffer.readSlot();

                log.log(packetInfo, "Item: {0}", item);
            
            } else {
                log.log(packetInfo, "No special data");
            }

        } else {
            throw new IOException("ID " + id + " is a invalid particle ID!");
        }
    }
    
}