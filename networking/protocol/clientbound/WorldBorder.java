package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x3D | S->C
public class WorldBorder {

    private WorldBorder() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int action = buffer.readVarInt();

        switch (action) {
            case 0:
                double diameter = buffer.readDouble();
                log.log(packetInfo, "Set diamater to {0}", diameter);
                break;
            
            case 1:
                double oldDiamter = buffer.readDouble();
                double newDiameter = buffer.readDouble();
                long speed = buffer.readVarLong();

                log.log(packetInfo, "Old Diameter: {0}, New Diameter: {1}, Speed: {2}", new Object[]{oldDiamter, newDiameter, speed});
                break;

            case 2:
                double x = buffer.readDouble();
                double z = buffer.readDouble();

                log.log(packetInfo, "Set center to X: {0}, Z: {1}", new Object[]{x, z});
                break;

            case 3:
                x = buffer.readDouble();
                z = buffer.readDouble();

                oldDiamter = buffer.readDouble();
                newDiameter = buffer.readDouble();

                speed = buffer.readVarLong();

                int portalTeleBoundary = buffer.readVarInt();

                int warningBlocks = buffer.readVarInt();
                int warningTime = buffer.readVarInt();

                log.log(packetInfo, "Initilize World Border, X: {0}, Z: {1}, Old Diameter: {2}, New Diameter: {3}", new Object[]{x, z, oldDiamter, newDiameter});
                log.log(packetInfo, "Speed: {0}, Portal Teleport Boundary: {1}, Warning Blocks: {2}, Warning Time: {3}", new Object[]{speed, portalTeleBoundary, warningBlocks, warningTime});
                break;

            case 4:
                warningTime = buffer.readVarInt();
                log.log(packetInfo, "Set warning time: {0}", warningTime);
                break;

            case 5:
                warningBlocks = buffer.readVarInt();
                log.log(packetInfo, "Set warning blocks: {0}", warningBlocks);
                break;

            default:
                throw new IOException("Action " + action + " is invalid!");

        }
    }
    
}