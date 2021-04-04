package networking.protocol;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.Server;
import networking.stream.MinecraftInputBuffer;

public class ClientboundManager implements Runnable {

    private final Logger log = Logger.getLogger(ClientboundManager.class.getName());
    private final ConsoleHandler consoleHandler = new ConsoleHandler();

    Socket socket;
    DataInputStream in;

    public ClientboundManager(Socket socket) throws IOException {
        this.socket = socket;

        in = new DataInputStream(socket.getInputStream());
 
        log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
    }

    @Override
    public void run() {

        while (!socket.isClosed()) {

            try {
                MinecraftInputBuffer buffer = new MinecraftInputBuffer(in);

                int packetId = buffer.readVarInt();

                if (packetId == -1) {
                    log.severe(Server.PREMATURE);
                }

                log.log(Level.FINER, "Reading packetID:0x{0}, Size: {1} Bytes\n", 
                        new Object[]{Integer.toHexString(packetId).toUpperCase(), buffer.size()});

                ClientboundPacket.getById(packetId).execute(buffer);

            } catch (IOException e) {

                try {
                    Thread.sleep(100);
                    log.finer("Thread is sleeping for 100ms!");

                    log.log(Level.SEVERE, "A IOException has occured!", e);
                    
                } catch (InterruptedException e2) {
                    log.log(Level.FINER, "Thread got interrupted!", e2);
    
                    Thread.currentThread().interrupt();
                }

            }
        }

        log.finer("Connection lost!");
    }  
}
