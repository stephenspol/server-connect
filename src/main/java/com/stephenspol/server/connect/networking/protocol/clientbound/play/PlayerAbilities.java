package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;
import com.stephenspol.server.connect.networking.Server;
import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

// Packet ID 0x30 | S->C
public class PlayerAbilities {

    private PlayerAbilities() {}
    
    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        /*Invulnerable	0x01
        Flying	0x02
        Allow Flying	0x04
        Creative Mode (Instant Break)	0x08
        */
        byte flags = buffer.readByte();
        float flySpeed = buffer.readFloat();
        float fov = buffer.readFloat();
        
        log.log(packetInfo, "Flag Value: {0}", flags);
        if (Server.getBit(flags, 0)) log.log(packetInfo, "Invulnerability Enabled");
        if (Server.getBit(flags, 1)) log.log(packetInfo, "Player is flying");
        if (Server.getBit(flags, 2)) log.log(packetInfo, "Flying Enabled");
        if (Server.getBit(flags, 3)) log.log(packetInfo, "Instant Break Enabled");
        
        log.log(packetInfo, "Fly Speed: {0}", flySpeed);
        log.log(packetInfo, "FOV: {0}\n", fov);
    }
    
}
