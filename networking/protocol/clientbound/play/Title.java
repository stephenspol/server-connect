package networking.protocol.clientbound.play;

import java.io.IOException;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

// Packet ID 0x4F | S->C
public class Title {

    private Title() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int action = buffer.readVarInt();

        switch (action) {
            case 0:
                String title = buffer.readString();
                log.log(packetInfo, "Set Title: {0}", title);
                break;
            
            case 1:
                String subtitle = buffer.readString();
                log.log(packetInfo, "Set Subtitle: {0}", subtitle);
                break;

            case 2:
                String actionBar = buffer.readString();
                log.log(packetInfo, "Set Action Bar Text: {0}", actionBar);
                break;

            case 3:
                int fadeIn = buffer.readInt();
                int stay = buffer.readInt();
                int fadeOut = buffer.readInt();
                log.log(packetInfo, "Fade In: {0}, Stay: {1}, Fade Out: {2}", new Object[]{fadeIn, stay, fadeOut});
                break;

            case 4:
                log.log(packetInfo, "Hide Title");
                break;

            case 5:
                log.log(packetInfo, "Reset Title");
                break;

            default:
                throw new IOException("Action " + action + " is invalid!");
        }
    }
    
}