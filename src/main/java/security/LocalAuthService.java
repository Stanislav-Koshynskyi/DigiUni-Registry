package security;

import entity.User;
import exception.UserNotFoundException;
import exception.WrongPasswordException;
import service.ServiceUserInterface;

import java.util.Optional;

public class LocalAuthService implements  AuthService {
    private final PasswordCoder passwordCoder;
    private final ServiceUserInterface serviceUser;
    private final SessionInfo sessionInfo;

    public LocalAuthService(PasswordCoder passwordCoder, ServiceUserInterface serviceUser, SessionInfo sessionInfo) {
        this.passwordCoder = passwordCoder;
        this.serviceUser = serviceUser;
        this.sessionInfo = sessionInfo;
    }

    @Override
    public void logout() {
        sessionInfo.logout();
    }

    @Override
    public User getCurrentUser() {
        return sessionInfo.getCurrentUser();
    }

    @Override
    public User login(String username, String password)throws UserNotFoundException, WrongPasswordException {
        Optional<User> userOpt = serviceUser.findUserByLogin(username);
        if (userOpt.isEmpty()) throw new UserNotFoundException("User not found");
        User user = userOpt.get();
        boolean passwordMatch = passwordCoder.matches(password, user.getPassword());
        if (!passwordMatch) throw new WrongPasswordException("Wrong password");
        sessionInfo.login(user);
        return user;
    }
}
