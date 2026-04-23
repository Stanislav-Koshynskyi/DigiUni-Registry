package socket;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 31337);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        Thread reader = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {System.out.println(line);}
            } catch (IOException e) {
                System.out.println("Server disconnected");
            }
        });
        reader.setDaemon(true);
        reader.start();

        String input;
        while ((input = console.readLine()) != null) {out.println(input);}
        socket.close();
    }
}