package socket;

import security.AuthService;
import ui.InputReader;
import ui.PageDisplay;
import util.PagerBuilder;

import java.net.Socket;

public class SocketClient implements Runnable {
    private final Socket socket;
    private final PagerBuilder pagerBuilder;
    private final AuthService authService;

    public SocketClient(Socket socket, PagerBuilder pagerBuilder, AuthService authService) {
        this.socket = socket;
        this.pagerBuilder = pagerBuilder;
        this.authService = authService;
    }

    @Override
    public void run() {
        try (socket) {
            InputReader reader = new SocketConsoleReader(socket);
            PageDisplay display = new PageDisplay(authService, reader);
            display.start(pagerBuilder.getUserPage(reader));
        } catch (Exception e) {
            System.out.println("Client disconnect " + e.getMessage());
        }
    }
}
