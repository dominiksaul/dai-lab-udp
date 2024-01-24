import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;

public class UDPListener implements Runnable{

    private final static String IPADDRESS = "239.255.22.5";
    private final static int PORT = 9904;
    private final Orchester orchester;

    public UDPListener(Orchester orchester) {
        this.orchester = orchester;
    }


    public void run() {
        try(socket; // Not strictly necessary, but this is allowed.
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
            var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

            System.out.println("Client connected");

            StringBuilder request = new StringBuilder();
            String line;
            while((line = in.readLine()) != null && line.length() != 0) {
                request.append(line);
            }
            String response = worker.work(request.toString());
            out.write(response);
            out.flush();

        } catch (IOException e) {
            System.err.println("Error in ClientHandler: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetSocketAddress group_address = new InetSocketAddress(IPADDRESS, PORT);
            NetworkInterface netif = NetworkInterface.getByName("eth0");
            socket.joinGroup(group_address, netif);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

            System.out.println("Received message: " + message + " from " + packet.getAddress() + ", port " + packet.getPort());
            socket.leaveGroup(group_address, netif);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
