package networking.protocol.clientbound;

import java.io.IOException;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;
import util.Icon;

// Packet ID 0x25 | S->C
public class MapData {

    private MapData() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int mapID = buffer.readVarInt();

        byte scale = buffer.readByte();
        boolean trackingPos = buffer.readBoolean();
        boolean locked = buffer.readBoolean();

        log.log(packetInfo, "Map ID: {0}, Scale: {1}, Tracking Position: {2}, Locked: {3}", new Object[]{mapID, scale, trackingPos, locked});

        int length = buffer.readVarInt();

        Icon[] icons = new Icon[length];

        for (int i = 0; i < length; i++) {
            int type = buffer.readVarInt();

            byte x = buffer.readByte();
            byte z = buffer.readByte();
            byte dir = buffer.readByte();

            boolean hasName = buffer.readBoolean();

            if (hasName) {
                String name = buffer.readString();

                icons[i] = new Icon(type, x, z, dir, name);
            } else {
                icons[i] = new Icon(type, x, z, dir);
            }

            log.log(packetInfo, "Icon {0}: {1}", new Object[]{i, icons[i]});
        }

        short columns = buffer.readUnsignedByte();

        log.log(packetInfo, "Columns: {0}", columns);

        if (columns > 0) {
            short rows = buffer.readUnsignedByte();

            byte x = buffer.readByte();
            byte z = buffer.readByte();

            length = buffer.readByte();

            short[] data = new short[length];

            for (int i = 0; i < length; i++) {
                data[i] = buffer.readUnsignedByte();
            }

            log.log(packetInfo, "Rows: {0}, X Offset: {1}, Z Offset: {2}", new Object[]{rows, x, z});

            log.log(packetInfo, "Data: {0}", data);
        }
    }
    
}