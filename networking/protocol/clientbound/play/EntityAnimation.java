package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x05 | S->C
public class EntityAnimation {

    private EntityAnimation() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        short animation = buffer.readUnsignedByte();

        log.log(packetInfo, "Entity ID {0}", entityID);

        switch (animation) {
            case 0:
                log.log(packetInfo, "Animation: Swing main arm");
                break;
            
            case 1:
                log.log(packetInfo, "Animation: Take damage");
                break;

            case 2:
                log.log(packetInfo, "Animation: Leave bed");
                break;
            
            case 3:
                log.log(packetInfo, "Animation: Swing offhand");
                break;

            case 4:
                log.log(packetInfo, "Animation: Critical effect");
                break;

            case 5:
                log.log(packetInfo, "Animation: Magic critical effect");
                break;

            default:
                throw new IOException("Animation " + animation + " is invalid!");
        }
    }
    
}