package socket;

import security.AuthService;
import security.LocalAuthService;
import security.LocalSessionInfo;
import security.PasswordCoder;
import service.ServiceUserInterface;
import util.PagerBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private final int port;
    private final PagerBuilder pagerBuilder;
    private final PasswordCoder coder;
    private final ServiceUserInterface serviceUser;

    public TCPServer(int port, PagerBuilder pagerBuilder, PasswordCoder coder, ServiceUserInterface serviceUser) {
        this.port = port;
        this.pagerBuilder = pagerBuilder;
        this.coder = coder;
        this.serviceUser = serviceUser;
    }

    public void start() throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("TCP Server start on port " + port);
            while (true) {
                Socket client = server.accept();
                System.out.println("Client connect: " + client.getInetAddress());
                AuthService clientAuth = new LocalAuthService(coder, serviceUser, new LocalSessionInfo());
                new Thread(new SocketClient(client, pagerBuilder, clientAuth)).start();
            }
        }
    }
}