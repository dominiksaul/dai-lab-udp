import java.io.*;
import java.net.*;

import static java.nio.charset.StandardCharsets.*;

public class TCPServer implements Runnable {
    private final static int PORT = 2205;
    private final Orchester orchester;
    public TCPServer(Orchester orchester) {
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

    public static void main(String args[]) {

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {

                try (Socket socket = serverSocket.accept(); var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8)); var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

                    String line;
                    while ((line = in.readLine()) != null) {
                        // There are two errors here!
                        out.write(line);
                    }

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }

}
