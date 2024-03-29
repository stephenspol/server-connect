package com.stephenspol.server.connect.networking.protocol;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;

import com.stephenspol.server.connect.networking.buffer.MinecraftInputBuffer;

import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.log;
import static com.stephenspol.server.connect.networking.protocol.ClientboundPacket.err;

public class ClientboundManager implements Runnable {

    private final Socket socket;
    private final DataInputStream in;

    private int state;

    private final ServerboundManager serverboundManager;
    private final ClientboundPacket packet;

    public ClientboundManager(Socket socket, int state, ServerboundManager serverboundManager) throws IOException {
        this.socket = socket;

        in = new DataInputStream(socket.getInputStream());

        this.state = state;

        this.serverboundManager = serverboundManager;

        packet = new ClientboundPacket(state, serverboundManager);
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

                packet.execute(buffer, packetId);

                if (state == 2 && packetId == 0x02) {
                    setState(3);
                    serverboundManager.setState(3);
                }

            } catch (EOFException e) {
                try {
                    log.log(Level.WARNING, "Stream has closed, closing socket...", e);
                    socket.close();
                } catch (IOException e2) {
                    
                    log.log(Level.SEVERE, "Unable to close socket!", e2);
                }
                

            } catch (IOException e) {

                log.log(Level.SEVERE, "A IOException has occured!", e);
                err.log(Level.SEVERE, "A IOException has occured!\n", e);

                if (buffer != null) {
                    err.log(Level.WARNING, "Packet Dump of packetID {0}: {1}", 
                            new Object[]{"0x" + Integer.toHexString(packetId).toUpperCase(), Arrays.toString(buffer.getBuffer())});
                }
            }
        }

        log.fine("Connection lost!");
    }

    public void setState(int state) {
        this.state = state;
        packet.setState(state);
    }
}
