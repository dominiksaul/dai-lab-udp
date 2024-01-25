import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Auditor {
    public static void main(String[] args) {
        Orchestra orchestra = new Orchestra();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(new TCPServer(orchestra));
            new UDPListener(orchestra).run();
        } catch (RuntimeException e) {
            System.out.println("Error with executor: " + e.getMessage());
        }
    }
}