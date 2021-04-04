package networking.protocol.clientbound;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.stream.MinecraftInputBuffer;

// Packet ID 0x42 | S->C
public class SpawnPosition {
    private static final Logger log = Logger.getLogger(SpawnPosition.class.getName());
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}

    private SpawnPosition() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException {
        int[] pos = buffer.readPos();
        
        log.fine("Spawn Position\n");

        log.log(Level.FINE, "X: {0}", pos[0]);
        log.log(Level.FINE, "Y: {0}", pos[1]);
        log.log(Level.FINE, "Z: {0}\n", pos[2]);
    }
    
}
