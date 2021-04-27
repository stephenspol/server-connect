package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

import networking.Server;
import networking.buffer.MinecraftInputBuffer;
import util.SoundCategory;

// Packet ID 0x52 | S->C
public class StopSound {

    private StopSound() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte flags = buffer.readByte();

        log.log(packetInfo, "Flag Value: {0}", flags);

        if (Server.getBit(flags, 0)) {
            int source = buffer.readVarInt();

            log.log(packetInfo, "Source: {0}", SoundCategory.getById(source).getName());
        }

        if (Server.getBit(flags, 1)) {
            String sound = buffer.readString();

            log.log(packetInfo, "Sound Name: {0}", sound);
        }
    }    
}