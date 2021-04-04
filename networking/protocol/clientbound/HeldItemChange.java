package networking.protocol.clientbound;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.stream.MinecraftInputBuffer;

// Packet ID 0x3F | S->C
public class HeldItemChange {
    private static final Logger log = Logger.getLogger(HeldItemChange.class.getName());
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}

    private HeldItemChange() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        byte slot = buffer.readByte();

        log.log(Level.FINE, "Player selected slot {0}\n", slot);
    }
    
}