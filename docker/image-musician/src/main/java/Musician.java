import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Musician {
    private final static String IPADDRESS = "239.255.22.5";
    private final static int PORT = 9904;

    public static void main(String[] args) {
        if (args.length != 1) throw RuntimeException("Invalid arguments")

        instrument = Instrument.valueOf(args[0]);
        if (instrument == null) throw RuntimeException("Invalid instrument")

        try (DatagramSocket socket = new DatagramSocket()) {
            String message = String.format("{\"uuid\": \"%s\", \"sound\": \"%s\"", UUID.randomUUID(), instrument.sound()}
            byte[] payload = message.getBytes(UTF_8);
            InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
            var packet = new DatagramPacket(payload, payload.length, dest_address);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            Runnable toRun = new Runnable() {
                public void run() {
                    socket.send(packet);
                }
            };
            ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate(toRun, 1, 1, TimeUnit.SECONDS);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
