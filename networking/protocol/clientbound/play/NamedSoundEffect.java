package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

import util.SoundCategory;

// Packet ID 0x18 | S->C
public class NamedSoundEffect {

    private NamedSoundEffect() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String soundName = buffer.readString();
        int soundCategory = buffer.readVarInt();

        double posX = buffer.readFixedPointNumberInt() * 4;
        double posY = buffer.readFixedPointNumberInt() * 4;
        double posZ = buffer.readFixedPointNumberInt() * 4;

        float volume = buffer.readFloat();
        float pitch = buffer.readFloat();

        log.log(packetInfo, "Sound Name: {0}, Sound Category: {1}", new Object[]{soundName, SoundCategory.getById(soundCategory).getName()});

        log.log(packetInfo, "Entity Postion X: {0}, Y: {1}, Z: {2}", new Object[]{posX, posY, posZ});

        log.log(packetInfo, "Volume: {0}%, Pitch: {1}", new Object[]{volume * 100, pitch});


    }
    
}