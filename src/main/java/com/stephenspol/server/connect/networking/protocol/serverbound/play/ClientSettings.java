package com.stephenspol.server.connect.networking.protocol.serverbound.play;

import com.stephenspol.server.connect.networking.buffer.MinecraftOutputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.packetInfo;
import static com.stephenspol.server.connect.networking.protocol.ServerboundPacket.log;

// Packet ID 0x05 | C->S
public class ClientSettings {

    private ClientSettings() {}

    public static byte[] execute(String locale, byte viewDist, int chatMode, boolean chatColor, short flags, int mainHand) {
        MinecraftOutputBuffer buffer = new MinecraftOutputBuffer();

        byte packetID = 0x05;

        buffer.writeByte(packetID);

        buffer.writeString(locale);

        buffer.writeByte(viewDist);

        buffer.writeVarInt(chatMode);
        buffer.writeBoolean(chatColor);

        buffer.writeShort(flags);

        buffer.writeVarInt(mainHand);

        log.log(packetInfo, "Writing packetID:0x{0}, Size: {1} Bytes\n", new Object[]{packetID, buffer.size()});

        log.log(packetInfo, "Locale: {0}, View Distance: {1}", new Object[]{locale, viewDist});

        log.log(packetInfo, "Chat Mode: {0}, Chat Color: {1}", new Object[]{chatMode, chatColor});

        log.log(packetInfo, "Flags: {0}, Main Hand: {1}", new Object[]{flags, mainHand});

        return buffer.getPacket();
    }
    
}