package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

import networking.Server;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x34 | S->C
public class PlayerPosAndLook {

    private PlayerPosAndLook() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();

        float yaw = buffer.readFloat();
        float pitch = buffer.readFloat();

        byte flags = buffer.readByte();

        int teleportID = buffer.readVarInt();

        log.log(packetInfo, "Flag Value: {0}", flags);

        if (Server.getBit(flags, 0)) {
            log.log(packetInfo, "Position X: {0} is Relative", x);
        } else {
            log.log(packetInfo, "Position X: {0} is Absolute", x);
        }
        
        if (Server.getBit(flags, 1)) {
            log.log(packetInfo, "Position Y: {0} is Relative", y);
        } else {
            log.log(packetInfo, "Position Y: {0} is Absolute", y);
        }

        if (Server.getBit(flags, 2)) {
            log.log(packetInfo, "Position Z: {0} is Relative", z);
        } else {
            log.log(packetInfo, "Position Z: {0} is Absolute", z);
        }

        if (Server.getBit(flags, 3)) {
            log.log(packetInfo, "Position Pitch: {0} is Relative", pitch);
        } else {
            log.log(packetInfo, "Position Pitch: {0} is Absolute", pitch);
        }

        if (Server.getBit(flags, 4)) {
            log.log(packetInfo, "Position Yaw: {0} is Relative", yaw);
        } else {
            log.log(packetInfo, "Position Yaw: {0} is Absolute", yaw);
        }

        log.log(packetInfo, "Teleport ID: {0}", teleportID);
        
    }
    
}
