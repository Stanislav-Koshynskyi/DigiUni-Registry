package security;

import entity.User;

public interface AuthService {
    void logout();
    User getCurrentUser();
    User login(String username, String password);
}
