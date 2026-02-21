package security;

import entity.User;

public interface SessionInfo {
    User getCurrentUser();
    void logout();
    void login(User user);
}
