package networking.protocol.clientbound;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.stream.MinecraftInputStream;

// Packet ID 0x17 | S->C
public class PluginMessage {
    private static final Logger log = Logger.getLogger(PluginMessage.class.getName());
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}

    private PluginMessage() {}

    public static void execute(MinecraftInputStream in) throws IOException{
        String channel = in.readString();
            
        log.log(Level.FINE, "Identifier: {0}", channel);
            
        String data = in.readString();
            
        log.log(Level.FINE, "Data: {0}\n", data);
    }
    
}
