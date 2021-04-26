package networking.protocol.clientbound.login;

import java.io.IOException;
import java.util.Arrays;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x01 | S->C
public class EncryptionRequest {

    private EncryptionRequest() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        String serverID = buffer.readString();

        int length = buffer.readVarInt();

        byte[] publicKey = buffer.readBytes(length);

        length = buffer.readVarInt();

        byte[] verifyToken = buffer.readBytes(length);

        log.log(packetInfo, "Server ID: {0}", serverID);

        log.log(packetInfo, "Public Key: {0}", Arrays.toString(publicKey));
        log.log(packetInfo, "Verify Token: {0}", Arrays.toString(verifyToken));
    }
    
}