package security;

import entity.Role;
import entity.User;

public class LocalSessionInfo implements  SessionInfo {
    User currentUser;
    User guestUser = new User(Role.GUEST, "none", "none");
    @Override
    public User getCurrentUser() {
        return currentUser == null ? guestUser : currentUser;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override
    public void login(User user) {
        currentUser = user;
    }
}
