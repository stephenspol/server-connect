package networking.protocol.clientbound.play;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;
import util.SoundCategory;

// Packet ID 0x50 | S->C
public class EntitySoundEffect {

    private EntitySoundEffect() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int soundID = buffer.readVarInt();
        int soundCategory = buffer.readVarInt();

        int entityID = buffer.readVarInt();

        float volume = buffer.readFloat();
        float pitch = buffer.readFloat();

        log.log(packetInfo, "Sound ID: {0}, Sound Category: {1}", new Object[]{soundID, SoundCategory.getById(soundCategory).getName()});

        log.log(packetInfo, "Entity ID: {0}", entityID);

        log.log(packetInfo, "Volume: {0}%, Pitch: {1}", new Object[]{volume * 100, pitch});


    }
    
}