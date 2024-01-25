import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;

public class Musician {
    private final static String IPADDRESS = "239.255.22.5";
    private final static int PORT = 9904;

    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("Invalid arguments");
        Instrument instrument = Instrument.valueOf(args[0]);

        String message = String.format("{\"uuid\": \"%s\", \"sound\": \"%s\"", UUID.randomUUID(), instrument.sound());

        byte[] payload = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(payload, payload.length, new InetSocketAddress(IPADDRESS, PORT));

        try (DatagramSocket socket = new DatagramSocket()) {
            while (true) {
                socket.send(packet);
                Thread.sleep(Duration.ofSeconds(5));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /* try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(new UDPSender(message));
        } catch (RuntimeException e) {
            System.out.println("Error musician: " + e.getMessage());
        } */
    }
}
