package networking.protocol.clientbound;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.Server;
import networking.stream.MinecraftInputBuffer;

// Packet ID 0x30 | S->C
public class PlayerAbilities {

    private static final Logger log = Logger.getLogger(PlayerAbilities.class.getName());
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}

    private PlayerAbilities() {}
    
    public static void execute(MinecraftInputBuffer buffer) throws IOException{
        /*Invulnerable	0x01
        Flying	0x02
        Allow Flying	0x04
        Creative Mode (Instant Break)	0x08
        */
        byte flags = buffer.readByte();
        float flySpeed = buffer.readFloat();
        float fov = buffer.readFloat();
        
        log.log(Level.FINE, "Flag Value: {0}", flags);
        if (Server.getBit(flags, 0)) log.fine("Invulnerability Enabled");
        if (Server.getBit(flags, 1)) log.fine("Player is flying");
        if (Server.getBit(flags, 2)) log.fine("Flying Enabled");
        if (Server.getBit(flags, 3)) log.fine("Instant Break Enabled");
        
        log.log(Level.FINE, "Fly Speed: {0}", flySpeed);
        log.log(Level.FINE, "FOV: {0}\n", fov);
    }
    
}
