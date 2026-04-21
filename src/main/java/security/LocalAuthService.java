package security;

import entity.Role;
import entity.User;
import exception.UserDeletedException;
import exception.UserNotFoundException;
import exception.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ServiceUserInterface;

import java.util.Optional;

@Slf4j
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
        User currentUser = sessionInfo.getCurrentUser();
        if (currentUser != null) {
            log.info("User {} logged out", currentUser.getLogin());
        } else {
            log.debug("Logout called, but no user was logged in.");
        }
        sessionInfo.logout();
    }

    @Override
    public User getCurrentUser() {
        return sessionInfo.getCurrentUser();
    }

    @Override
    public User login(String username, String password)throws UserNotFoundException, WrongPasswordException {
        log.info("Trying to authenticate with username {}", username);
        Optional<User> userOpt = serviceUser.findUserByLogin(username);
        if (userOpt.isEmpty()) {
            log.warn("User {} not found", username);
            throw new UserNotFoundException("User not found");
        };
        User user = userOpt.get();
        boolean passwordMatch = passwordCoder.matches(password, user.getPassword());
        if (!passwordMatch) {
            log.warn("User {} has incorrect password", username);
            throw new WrongPasswordException("Wrong password");
        }
        if (user.getRole().equals(Role.DELETED)) {
            log.warn("User {} trying logging when has been deleted", username);
            throw new UserDeletedException("User deleted");
        }
        log.info("User {} logged in", username);
        sessionInfo.login(user);
        return user;
    }
}
