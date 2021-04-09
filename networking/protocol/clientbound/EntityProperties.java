package networking.protocol.clientbound;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;
import util.Modifier;
import util.Property;

// Packet ID 0x58 | S->C
public class EntityProperties {

    private EntityProperties() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();

        int length = buffer.readInt();

        Property[] properties = new Property[length];

        for (int i = 0; i < length; i++) {
            String key = buffer.readString();
            double value = buffer.readDouble();

            int size = buffer.readVarInt();

            Modifier[] modifiers = new Modifier[size];

            for (int j = 0; j < size; j++) {
                UUID uuid = buffer.readUUID();
                double amount = buffer.readDouble();

                byte operation = buffer.readByte();

                modifiers[j] = new Modifier(uuid, amount, operation);
            }

            properties[i] = new Property(key, value, modifiers);
        }

        log.log(packetInfo, "Entity properties of entity {0}", entityID);

        log.log(packetInfo, "Properties: {0}", Arrays.toString(properties));
    }
    
}