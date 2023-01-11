package networking.protocol;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import networking.protocol.serverbound.handshake.Handshake;

import static networking.protocol.ServerboundPacket.log;
import static networking.protocol.ServerboundPacket.err;

public class ServerboundManager implements Runnable {

    private final Socket socket;
    private final DataOutputStream out;

    private final Map<Integer, Object[]> packetBuffer;

    private int state;

    private final ServerboundPacket packet;

    public ServerboundManager(Socket socket, int state) throws IOException {
        this.socket = socket;

        out = new DataOutputStream(socket.getOutputStream());

        this.state = state;

        packetBuffer = new LinkedHashMap<>(); 
        
        packet = new ServerboundPacket(state);
    }

    public void execute(int packetId, Object... args) {
        packetBuffer.put(packetId, args);
    }

    @Override
    public void run() {

        sendHandshake();

        while (!socket.isClosed()) {

            int packetId = 0;

            byte[] payload = null;

            Map<Integer, Object[]> tempPacketBuffer = new LinkedHashMap<>(packetBuffer);

            packetBuffer.clear();

            Iterator<Entry<Integer, Object[]>> packetIterator = tempPacketBuffer.entrySet().iterator();
            packetBuffer.clear();

            while (packetIterator.hasNext()) {
                try {
                    Entry<Integer, Object[]> entry = packetIterator.next();

                    packetId = entry.getKey();

                    payload = packet.execute(packetId, entry.getValue());

                    out.write(payload);

                } catch (IOException e) {

                    log.log(Level.SEVERE, "A IOException has occured!", e);
                    err.log(Level.SEVERE, "A IOException has occured!\n", e);

                    if (payload != null) {
                        err.log(Level.WARNING, "Packet Dump of packetID {0}: {1}", 
                                new Object[]{"0x" + Integer.toHexString(packetId).toUpperCase(), Arrays.toString(payload)});
                    }
                }
            }
        }

        log.fine("Connection lost!");
    }

    private void sendHandshake() {
        try {
            String host = socket.getInetAddress().getHostAddress();

            out.write(Handshake.execute(host, socket.getPort(), state));

        } catch (IOException e1) {
            log.log(Level.SEVERE, "A IOException has occured! Handshake has failed!", e1);
            err.log(Level.SEVERE, "A IOException has occured! Handshake has failed!\n", e1);

            try {
                socket.close();
            } catch (IOException e2) {
                
                log.log(Level.SEVERE, "Unable to close socket!", e2);
            }
        }
    }

    public void setState(int state) {
        this.state = state;
        packet.setState(state);
    }
}
