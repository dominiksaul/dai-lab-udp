import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Musician {
    private static final Instrument instrument;
    public static void main(String[] args) {
        if (args.length != 1) throw RuntimeException("")


        Orchestra orchester = new Orchestra();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(new TCPServer(orchester));
            executor.execute(new UDPListener(orchester));
        } catch (RuntimeException e) {
            System.out.println("Error with executor: " + e.getMessage());
        }
    }

}
