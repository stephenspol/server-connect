package networking.protocol.clientbound;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.stream.MinecraftInputBuffer;

// Packet ID 0x0D | S->C
public class ServerDifficulty {

    private static final Logger log = Logger.getLogger(ServerDifficulty.class.getName());
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}

    private ServerDifficulty() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException {
        int difficulty = buffer.readUnsignedByte();
            
        log.log(Level.FINE, "Server Difficulty: {0}", difficulty);

        boolean locked = buffer.readBoolean();

        log.log(Level.FINE, "Difficulty Locked: {0}\n", locked); 
    }
    
}
