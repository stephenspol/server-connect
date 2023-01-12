package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x0C | S->C
public class BossBar {

    private BossBar() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        UUID uuid = buffer.readUUID();
        int action = buffer.readVarInt();

        log.log(packetInfo, "Boss Bar {0}", uuid);

        switch (action) {
            case 0: 
                String title = buffer.readString();
                float health = buffer.readFloat();
                int color = buffer.readVarInt();
                int division = buffer.readVarInt();

                short flags = buffer.readUnsignedByte();

                log.log(packetInfo, "Create new Boss Bar");
                log.log(packetInfo, "Title: {0}, Health: {1}, Color: {2}, Division: {3}, Flags: {4}", new Object[]{title, health, color, division, flags});
                break;

            case 1:
                log.log(packetInfo, "Remove Boss Bar");
                break;

            case 2:
                health = buffer.readFloat();

                log.log(packetInfo, "Update Boss Bar health to {0}", health);
                break;
            
            case 3:
                title = buffer.readString();

                log.log(packetInfo, "Update Boss Bar title to {0}", title);
                break;

            case 4:
                color = buffer.readVarInt();
                division = buffer.readVarInt();

                log.log(packetInfo, "Update Boss Bar style, Color: {0}, Division: {1}", new Object[]{color, division});
                break;

            case 5:
                flags = buffer.readUnsignedByte();

                log.log(packetInfo, "Update Boss Bar flags to {0}", flags);
                break;

            default:
                throw new IOException("Action " + action + " is invalid!");
        }
    }
    
}