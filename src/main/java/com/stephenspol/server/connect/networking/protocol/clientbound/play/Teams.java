package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

// Packet ID 0x4C | S->C
public class Teams {

    private Teams() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String teamName = buffer.readString();

        log.log(packetInfo, "Team Name: {0}", teamName);

        byte mode = buffer.readByte();

        switch (mode) {
            case 0:
            case 2:
                if (mode == 0) log.log(packetInfo, "Create Team");
                else log.log(packetInfo, "Update Team Info");


                String teamNameDisplay = buffer.readString();
                byte friendlyFlags = buffer.readByte();

                String nameTagVisibility = buffer.readString();
                String collusionRule = buffer.readString();

                int teamColor = buffer.readVarInt();
                String teamPrefix = buffer.readString();
                String teamSuffix = buffer.readString();

                log.log(packetInfo, "Team Name Display: {0}, Friendly Flags: {1}", new Object[]{teamNameDisplay, friendlyFlags});
                log.log(packetInfo, "Name Tag Visibility: {0}, Collusion Rule: {1}", new Object[]{nameTagVisibility, collusionRule});
                log.log(packetInfo, "Team Color: {0}, Team Prefix: {1}, Team Suffix: {2}", new Object[]{teamColor, teamPrefix, teamSuffix});

                if (mode == 0) {

                    int length = buffer.readVarInt();

                    log.log(packetInfo, "Entities by UUID:");

                    String[] entities = new String[length];

                    for (int i = 0; i < length; i++) {
                        entities[i] = buffer.readString();
                        log.log(packetInfo, "Entity {0}: {1}", new Object[]{i, entities});
                    }
                }

                break;
            
            case 1:
                log.log(packetInfo, "Remove Team");
                break;
                
            case 3:
            case 4:
                if (mode == 3) log.log(packetInfo, "Add Players to Team");
                else log.log(packetInfo, "Remove players from Team");

                int length = buffer.readVarInt();

                log.log(packetInfo, "Entities by UUID:");

                String[] entities = new String[length];

                for (int i = 0; i < length; i++) {
                    entities[i] = buffer.readString();
                    log.log(packetInfo, "Entity {0}: {1}", new Object[]{i, entities});
                }

                break;

            default:
                throw new IOException("Mode " + mode + " is a invalid!");
        }
    }
    
}