import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Auditor {
    public static void main(String[] args) {
        Orchestra orchester = new Orchestra();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(new TCPServer(orchester));
            executor.execute(new UDPListener(orchester));
        } catch (RuntimeException e) {
            System.out.println("Error with executor: " + e.getMessage());
        }
    }
}