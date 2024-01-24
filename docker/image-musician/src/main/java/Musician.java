import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Musician {
    public static void main(String[] args) {
        if (args.length != 1) throw new RuntimeException("Invalid arguments");

        Instrument instrument = Instrument.valueOf(args[0]);
        if (instrument == null) throw new RuntimeException("Invalid instrument");

        String message = String.format("{\"uuid\": \"%s\", \"sound\": \"%s\"", UUID.randomUUID(), instrument.sound());

        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {
            Runnable UDPSender = new UDPSender(message);
            ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate(UDPSender, 1, 1, TimeUnit.SECONDS);
        } catch (RuntimeException e) {
            System.out.println("Error musician: " + e.getMessage());
        }

    }
}
