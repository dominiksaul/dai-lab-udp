import java.io.*;
import java.net.*;

import static java.nio.charset.StandardCharsets.*;

public class TCPServer implements Runnable {
    private final static int PORT = 2205;
    private final Orchestra orchestra;

    public TCPServer(Orchestra orchestra) {
        this.orchestra = orchestra;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {
                    System.out.println("TCP Server request received");
                    out.write(orchestra.getActiveMusicians());
                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }
}
