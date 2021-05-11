package networking.protocol.serverbound.play;

import networking.buffer.MinecraftOutputBuffer;

import static networking.protocol.ServerboundPacket.packetInfo;
import static networking.protocol.ServerboundPacket.log;

// Packet ID 0x01 | C->S
public class QueryBlockNBT {

    private QueryBlockNBT() {}

    public static byte[] execute(int transactionID, int[] pos) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x01;

        buffer.writeByte(packetID);

        buffer.writeVarInt(transactionID);

        buffer.writePos(pos);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Transaction ID: {0}", transactionID);
        log.log(packetInfo, "Location, X: {0}, Y: {1}, Z: {2}", new Object[]{pos[0], pos[1], pos[2]});

        return buffer.getPacket();
    }
    
}