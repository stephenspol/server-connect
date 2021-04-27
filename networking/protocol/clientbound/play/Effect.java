package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

import util.EffectType;

// Packet ID 0x21 | S->C
public class Effect {

    private Effect() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int effectID = buffer.readInt();
        
        int[] pos = buffer.readPos();

        log.log(packetInfo, "Effect ID: {0}, Effect Name: {1}", new Object[]{effectID, EffectType.getById(effectID).getName()});
        log.log(packetInfo, "Position, X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});

        if (effectID == 1010) {
            int recordID = buffer.readInt();

            log.log(packetInfo, "Record ID: {0}", recordID);
        
        } else if (effectID == 2000) {
            int dir = buffer.readInt();

            log.log(packetInfo, "Direction: {0}", dir);

        } else if (effectID == 2001) {
            int blockState = buffer.readInt();

            log.log(packetInfo, "Block State: {0}", blockState);

        } else if (effectID == 2002 || effectID == 2007) {
            int rgb = buffer.readInt();

            log.log(packetInfo, "Color: #{0}", Integer.toHexString(rgb));

        } else if (effectID == 2005) {
            int count = buffer.readInt();

            log.log(packetInfo, "Number of particles: {0}", count);
        
        } else {
            log.log(packetInfo, "No special data");
        }

        boolean disableRelVolume = buffer.readBoolean();

        log.log(packetInfo, "Disable Relative Volume: {0}", disableRelVolume);
    }
    
}