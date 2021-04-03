package networking.protocol;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.Server;
import networking.stream.MinecraftInputStream;

public class ClientboundManager implements Runnable {

    private final Logger log = Logger.getLogger(ClientboundManager.class.getName());
    private final ConsoleHandler consoleHandler = new ConsoleHandler();

    Socket socket;
    MinecraftInputStream in;

    public ClientboundManager(Socket socket) throws IOException {
        this.socket = socket;

        in = new MinecraftInputStream(socket.getInputStream());
 
        log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
    }

    @Override
    public void run() {

        while (!socket.isClosed()) {

            try {
                int size = in.readVarInt();

                if (size == -1) {
                    //log.severe(Server.PREMATURE);

                    Thread.sleep(100);
                    log.finer("Thread is sleeping for 100ms!");
                    
                    continue;
                }

                int packetId = in.readVarInt();

                if (packetId == -1) {
                    log.severe(Server.PREMATURE);
                }

                if (size == 0) {
                    log.severe(Server.INVALID_LENGTH);
                }

                log.log(Level.FINER, "Reading packetID:0x{0}, Size: {1} Bytes\n", 
                        new Object[]{Integer.toHexString(packetId).toUpperCase(), size});

                ClientboundPacket.getById(packetId).execute(in);

            } catch (IOException e) {
                log.log(Level.SEVERE, "A IOException has occured!", e);
                break;

            } catch (InterruptedException e) {
                log.log(Level.FINER, "Thread got interrupted!", e);

                Thread.currentThread().interrupt();
            }
        }

        log.finer("Connection lost!");
    }  
}
