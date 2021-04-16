package networking.protocol;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;

import static networking.protocol.ClientboundPacket.log;
import static networking.protocol.ClientboundPacket.err;

import networking.stream.MinecraftInputBuffer;

public class ClientboundManager implements Runnable {

    private final Socket socket;
    private final DataInputStream in;

    public ClientboundManager(Socket socket) throws IOException {
        this.socket = socket;

        in = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {

        while (!socket.isClosed()) {

            MinecraftInputBuffer buffer = null;
            int packetId = 0;

            try {
                buffer = new MinecraftInputBuffer(in);

                packetId = buffer.readVarInt();

                log.log(Level.FINE, "Reading packetID:0x{0}, Size: {1} Bytes\n", 
                        new Object[]{Integer.toHexString(packetId).toUpperCase(), buffer.size()});

                ClientboundPacket.getById(packetId).execute(buffer);

            } catch (EOFException e) {
                try {
                    log.log(Level.SEVERE, "Stream has closed, closing socket...", e);
                    socket.close();
                } catch (IOException e2) {
                    
                    log.log(Level.SEVERE, "Unable to close socket!", e2);
                }
                

            } catch (IOException e) {

                log.log(Level.SEVERE, "A IOException has occured!", e);
                err.log(Level.SEVERE, "A IOException has occured!\n", e);

                err.log(Level.WARNING, "Packet Dump of packetID {0}: {1}", 
                        new Object[]{"0x" + Integer.toHexString(packetId).toUpperCase(), Arrays.toString(buffer.getBuffer())});
            }
        }

        log.fine("Connection lost!");
    }  
}
