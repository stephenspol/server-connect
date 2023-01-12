package com.stephenspol.server.connect.networking.protocol.clientbound.play;

import java.io.IOException;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;
import com.stephenspol.server.connect.nbt.Tag;
import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

// Packet ID 0x24 | S->C
public class JoinGame {

    private JoinGame() {}

    
    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int eid = buffer.readInt();
        boolean isHardcore = buffer.readBoolean();
        int gameMode = buffer.readUnsignedByte();
        byte prevGameMode = buffer.readByte();

        int worldCount = buffer.readVarInt();

        String[] worldNames = new String[worldCount];

        for (int i = 0; i < worldNames.length; i++)
        {
            worldNames[i] = buffer.readString();
        }

        Tag<?> dimCodec = buffer.readNBT();
        Tag<?> dim = buffer.readNBT();

        String worldName = buffer.readString();
        long hashedSeed = buffer.readLong();

        int maxPlayers = buffer.readVarInt();
        int viewDist = buffer.readVarInt();
        
        boolean reducedDebug = buffer.readBoolean();
        boolean enableRespawnScr = buffer.readBoolean();
        boolean isDebug = buffer.readBoolean();

        boolean isFlat = buffer.readBoolean();
        
        log.log(packetInfo, "Entity ID: {0}", eid);
        log.log(packetInfo, "Is Hardcore: {0}", isHardcore);
        log.log(packetInfo, "Game Mode: {0}", gameMode);
        log.log(packetInfo, "Prev Game Mode: {0}", prevGameMode);
        log.log(packetInfo, "Dimension Codec: {0}", dimCodec);
        log.log(packetInfo, "Dimension: {0}", dim);
        log.log(packetInfo, "World Name: {0}", worldName);
        log.log(packetInfo, "Hashed seed: {0}", hashedSeed);
        log.log(packetInfo, "Max Players: {0}", maxPlayers);
        log.log(packetInfo, "View Distance: {0}", viewDist);
        log.log(packetInfo, "Reduced Debug Info: {0}", reducedDebug);
        log.log(packetInfo, "Enable Respawn Screen: {0}", enableRespawnScr);
        log.log(packetInfo, "Is Debug Enabled: {0}", isDebug);
        log.log(packetInfo, "Is a Flat World: {0}\n", isFlat);
    }
    
}
