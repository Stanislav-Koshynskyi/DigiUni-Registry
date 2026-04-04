package exception;

public class LinkedException extends RuntimeException {
    public LinkedException(String message) {
        super(message);
    }
    public LinkedException(String message, Throwable cause) {}
}
