package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;

import com.stephenspol.server.connect.networking.Server;
import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

// Packet ID 0x59 | S->C
public class EntityEffect {

    private EntityEffect() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int entityID = buffer.readVarInt();
        
        byte effectID = buffer.readByte();
        byte amplifier = buffer.readByte();
        int duration = buffer.readVarInt();

        byte flags = buffer.readByte();

        log.log(packetInfo, "Entity ID: {0}", entityID);

        log.log(packetInfo, "Effect ID {0}, level {1}, duration {2} ticks", new Object[]{effectID, amplifier + 1, duration});

        if (Server.getBit(flags, 0)) log.log(packetInfo, "Effect is ambient");
        if (Server.getBit(flags, 1)) log.log(packetInfo, "Particles are shown");
        if (Server.getBit(flags, 2)) log.log(packetInfo, "Show icon");
    }
    
}
