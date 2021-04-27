package networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

import util.advancement.Advancement;

// Packet ID 0x57 | S->C
public class Advancements {

    private Advancements() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        boolean reset = buffer.readBoolean();

        int length = buffer.readVarInt();

        Map<String, Advancement> advancements = new HashMap<>();

        for (int i = 0; i < length; i++) {
            advancements.put(buffer.readString(), buffer.readAdvancement());
        }

        length = buffer.readVarInt();

        String[] toRemove = new String[length];

        for (int i = 0; i < length; i++) {
            toRemove[i] = buffer.readString();
        }

        length = buffer.readVarInt();

        Map<String, Map<String, Long>> progress = new HashMap<>();

        for (int i = 0; i < length; i++) {
            progress.put(buffer.readString(), buffer.readAdvancementProgress());
        }

        log.log(packetInfo, "Clear/Reset advancements: {0}", reset);
        log.log(packetInfo, "Advancements: {0}", advancements);
        log.log(packetInfo, "Advancements to removed: {0}", Arrays.toString(toRemove));
        log.log(packetInfo, "Progress: {0}", progress);
    }
    
}