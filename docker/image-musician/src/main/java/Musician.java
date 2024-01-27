import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Musician {
    private final static String IPADDRESS = "239.255.22.5";
    private final static int PORT = 9904;
    private final static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, srcType, context) -> new JsonPrimitive(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").format(localDateTime))).setPrettyPrinting().create();

    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("Invalid arguments");
        Instrument instrument = Instrument.valueOf(args[0]);

        try (DatagramSocket socket = new DatagramSocket()) {
            // Solution where musician doesn't send Date/Time of his last activity
            MusicianMessage musicianMessage = new MusicianMessage(UUID.randomUUID().toString(), instrument.sound());
            String message = gson.toJson(musicianMessage);
            byte[] payload = message.getBytes(StandardCharsets.UTF_8);
            InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
            DatagramPacket packet = new DatagramPacket(payload, payload.length, dest_address);

            while (true) {
                /*
                // Alternative solution where musician sends Date/Time of his last activity
                MusicianMessage musicianMessage = new MusicianMessage(UUID.randomUUID().toString(), instrument.sound(), LocalDateTime.now());
                String message = gson.toJson(musicianMessage);
                byte[] payload = message.getBytes(StandardCharsets.UTF_8);
                InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
                DatagramPacket packet = new DatagramPacket(payload, payload.length, dest_address);
                 */

                System.out.println(message);
                socket.send(packet);
                Thread.sleep(Duration.ofSeconds(1));
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
