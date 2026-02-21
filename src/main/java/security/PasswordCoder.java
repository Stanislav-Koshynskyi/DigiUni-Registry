package security;

public interface PasswordCoder {
    String encodePassword(String password);
    boolean matches(String password, String encodedPassword);
}
