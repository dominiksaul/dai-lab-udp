import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPSender implements Runnable {
    private final static String IPADDRESS = "239.255.22.5";
    private final static int PORT = 9904;
    private final String message;
    private final byte[] payload;

    private final DatagramPacket packet;

    public UDPSender(String m) {
        this.message = m;
        this.payload = message.getBytes(StandardCharsets.UTF_8);
        this.packet = new DatagramPacket(payload, payload.length, new InetSocketAddress(IPADDRESS, PORT));
    }

    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.send(packet);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
