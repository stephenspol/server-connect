package networking.protocol.clientbound.play;

import java.io.IOException;
import java.util.UUID;

import networking.buffer.MinecraftInputBuffer;

import static networking.protocol.ClientboundPacket.packetInfo;
import static networking.protocol.ClientboundPacket.log;

import util.Player;
import util.PlayerProperty;

// Packet ID 0x32 | S->C
public class PlayerInfo {

    private PlayerInfo() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        int action = buffer.readVarInt();

        int length = buffer.readVarInt();

        Player[] players = new Player[length];

        for (int i = 0; i < length; i++) {
            UUID uuid = buffer.readUUID();

            switch(action) {
                case 0:
                    String name = buffer.readString();

                    PlayerProperty[] properties = readProperties(buffer);

                    int gameMode = buffer.readVarInt();
                    int ping = buffer.readVarInt();

                    boolean hasCustomName = buffer.readBoolean();

                    if (hasCustomName) {
                        String customName = buffer.readString();

                        players[i] = new Player(uuid, name, properties, gameMode, ping, customName);

                        break;
                    }

                    players[i] = new Player(uuid, name, properties, gameMode, ping);
                    log.log(packetInfo, "Player added: {0}", players[i]);
                    break;
                
                case 1:
                    gameMode = buffer.readVarInt();
                    log.log(packetInfo, "Updated {0} Game Mode to {1}", new Object[]{uuid, gameMode});
                    break;

                case 2:
                    ping = buffer.readVarInt();
                    log.log(packetInfo, "Updated {0} Ping to {1}", new Object[]{uuid, ping});
                    break;

                case 3:
                    hasCustomName = buffer.readBoolean();
                    
                    if (hasCustomName) {
                        String customName = buffer.readString();

                        log.log(packetInfo, "Updated {0} Custom Name to {1}", new Object[]{uuid, customName});
                        break;
                    }

                    log.log(packetInfo, "Updated {0} Custome Name to null", uuid);
                    break;

                case 4:
                    log.log(packetInfo, "Removed {0}", uuid);
                    break;

                default:
                    throw new IOException("Action: " + action + " is invalid!");

            }
        }
    }

    private static PlayerProperty[] readProperties(MinecraftInputBuffer buffer) throws IOException {
        int length = buffer.readVarInt();

        PlayerProperty[] properties = new PlayerProperty[length];

        for (int i = 0; i < length; i++) {
            String name = buffer.readString();
            String value = buffer.readString();

            boolean isSigned = buffer.readBoolean();

            if (isSigned) {
                String signature = buffer.readString();

                properties[i] = new PlayerProperty(name, value, signature);
                
                continue;
            }

            properties[i] = new PlayerProperty(name, value);
        }

        return properties;
    }
    
}
