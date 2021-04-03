package networking.protocol.clientbound;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import NBT.Tag;
import networking.stream.MinecraftInputStream;

// Packet ID 0x24 | S->C
public class JoinGame {

    private static final Logger log = Logger.getLogger(JoinGame.class.getName());
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}

    private JoinGame() {}

    
    public static void execute(MinecraftInputStream in) throws IOException{
        int eid = in.readInt();
        boolean isHardcore = in.readBoolean();
        int gameMode = in.readUnsignedByte();
        byte prevGameMode = in.readByte();

        int worldCount = in.readVarInt();

        String[] worldNames = new String[worldCount];

        for (int i = 0; i < worldNames.length; i++)
        {
            worldNames[i] = in.readString();
        }

        Tag<?> dimCodec = in.readNBT();
        Tag<?> dim = in.readNBT();

        String worldName = in.readString();
        long hashedSeed = in.readLong();

        int maxPlayers = in.readVarInt();
        int viewDist = in.readVarInt();
        
        boolean reducedDebug = in.readBoolean();
        boolean enableRespawnScr = in.readBoolean();
        boolean isDebug = in.readBoolean();

        boolean isFlat = in.readBoolean();
        
        log.log(Level.FINE, "Entity ID: {0}", eid);
        log.log(Level.FINE, "Is Hardcore: {0}", isHardcore);
        log.log(Level.FINE, "Game Mode: {0}", gameMode);
        log.log(Level.FINE, "Prev Game Mode: {0}", prevGameMode);
        log.log(Level.FINE, "Dimension Codec: {0}", dimCodec);
        log.log(Level.FINE, "Dimension: {0}", dim);
        log.log(Level.FINE, "World Name: {0}", worldName);
        log.log(Level.FINE, "Hashed seed: {0}", hashedSeed);
        log.log(Level.FINE, "Max Players: {0}", maxPlayers);
        log.log(Level.FINE, "View Distance: {0}", viewDist);
        log.log(Level.FINE, "Reduced Debug Info: {0}", reducedDebug);
        log.log(Level.FINE, "Enable Respawn Screen: {0}", enableRespawnScr);
        log.log(Level.FINE, "Is Debug Enabled: {0}", isDebug);
        log.log(Level.FINE, "Is a Flat World: {0}\n", isFlat);
    }
    
}
