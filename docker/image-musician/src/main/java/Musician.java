import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Musician {
    private final static String IPADDRESS = "239.255.22.5";
    private final static int PORT = 9904;

    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("Invalid arguments");
        Instrument instrument = Instrument.valueOf(args[0]);

        try (DatagramSocket socket = new DatagramSocket()) {
            /* String message = String.format("{\"uuid\": \"%s\", \"sound\": \"%s\"}", UUID.randomUUID(), instrument.sound());
            byte[] payload = message.getBytes(StandardCharsets.UTF_8);
            InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
            DatagramPacket packet = new DatagramPacket(payload, payload.length, dest_address); */

            while (true) {

                String message = String.format("{\"uuid\": \"%s\", \"sound\": \"%s\", \"lastActivity\" : %s}", UUID.randomUUID(), instrument.sound(), LocalDateTime.now());
            byte[] payload = message.getBytes(StandardCharsets.UTF_8);
            InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
            DatagramPacket packet = new DatagramPacket(payload, payload.length, dest_address);
                System.out.println(message);
                socket.send(packet);
                Thread.sleep(Duration.ofSeconds(5));
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
