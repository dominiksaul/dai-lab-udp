import com.google.gson.Gson;

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
    private final static Gson gson = new Gson();

    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("Invalid arguments");
        Instrument instrument = Instrument.valueOf(args[0]);

        try (DatagramSocket socket = new DatagramSocket()) {
            MusicianSound musicianSound = new MusicianSound(UUID.randomUUID().toString(), instrument.sound());
            String message = gson.toJson(musicianSound);
            byte[] payload = message.getBytes(StandardCharsets.UTF_8);
            InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
            DatagramPacket packet = new DatagramPacket(payload, payload.length, dest_address);

            while (true) {
                System.out.println(message);
                socket.send(packet);
                Thread.sleep(Duration.ofSeconds(1));
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private record MusicianSound(String uuid, String sound) {
    }
}
