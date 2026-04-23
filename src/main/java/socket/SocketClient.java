package socket;

import security.AuthService;
import ui.InputReader;
import ui.PageDisplay;
import util.PagerBuilder;

import java.net.Socket;

public class SocketClient implements Runnable {
    private final Socket socket;
    private final PagerBuilder pagerBuilder;
    private final InputReader reader;

    public SocketClient(Socket socket, PagerBuilder pagerBuilder, InputReader reader) {
        this.socket = socket;
        this.pagerBuilder = pagerBuilder;
        this.reader = reader;
    }

    @Override
    public void run() {
        try (socket) {
            PageDisplay display = new PageDisplay(pagerBuilder.getAuthService(), reader);
            display.start(new MainPage(reader, pagerBuilder));
        } catch (Exception e) {
            System.out.println("Client disconnect: " + e.getMessage());
        }
    }
}