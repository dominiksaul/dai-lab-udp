import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;

public class UDPListener implements Runnable {
    private final static String IPADDRESS = "239.255.22.5";
    private final static int PORT = 9904;
    private final Orchestra orchestra;

    public UDPListener(Orchestra orchestra) {
        this.orchestra = orchestra;
    }

    public void run() {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetSocketAddress group_address = new InetSocketAddress(IPADDRESS, PORT);
            NetworkInterface netif = NetworkInterface.getByName("eth0");
            socket.joinGroup(group_address, netif);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                System.out.println("Received message: " + message + " from " + packet.getAddress() + ", port " + packet.getPort());
                orchestra.registerMusician(message);
                System.out.println("test");
                packet.setLength(buffer.length);
            }
            // socket.leaveGroup(group_address, netif);
        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }
    }
}
