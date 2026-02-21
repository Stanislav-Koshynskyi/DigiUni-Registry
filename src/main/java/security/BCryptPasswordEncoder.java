package security;

import com.password4j.Password;

public class BCryptPasswordEncoder implements PasswordCoder {
    @Override
    public String encodePassword(String password) {
        return Password.hash(password).withBcrypt().getResult();
    }
    @Override
    public boolean matches(String password, String encodedPassword) {
        return Password.check(password, encodedPassword).withBcrypt();
    }
}
