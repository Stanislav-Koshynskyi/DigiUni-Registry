package exception;

public class WrongPasswordException extends AppException {
    public WrongPasswordException(String message) {
        super(message);
    }
}
