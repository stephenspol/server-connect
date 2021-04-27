package networking.protocol.clientbound.play;

import java.io.IOException;

import nbt.Tag;
import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x39 | S->C
public class Respawn {

    private Respawn() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        Tag<?> dimension = buffer.readNBT();
        String worldName = buffer.readString();
        long hashedSeed = buffer.readLong();

        short gameMode = buffer.readUnsignedByte();
        short prevGameMode = buffer.readUnsignedByte();

        boolean isDebug = buffer.readBoolean();
        boolean isFlat = buffer.readBoolean();
        boolean copyMetadata = buffer.readBoolean();

        log.log(packetInfo, "Dimension: {0}", dimension);
        log.log(packetInfo, "World Name: {0}", worldName);
        log.log(packetInfo, "Hashed Seed: {0}", hashedSeed);

        log.log(packetInfo, "Game Mode: {0}", gameMode);
        log.log(packetInfo, "Previous Game Mode: {0}", prevGameMode);

        log.log(packetInfo, "Is Debug World: {0}", isDebug);
        log.log(packetInfo, "Is Flat World: {0}", isFlat);
        log.log(packetInfo, "Copy Metadata: {0}", copyMetadata);
    }
    
}