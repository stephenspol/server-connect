package networking.protocol.clientbound;

import java.io.IOException;

import nbt.Tag;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x54 | S->C
public class NBTQueryResponse {

    private NBTQueryResponse() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int transactionID = buffer.readVarInt();

        Tag<?> nbt = buffer.readNBT();

        log.log(packetInfo, "Transaction ID: {0}", transactionID);
        log.log(packetInfo, "NBT Data: {0}", nbt);
    }
    
}