package entity;

public class User {
    Role role;
    String password;
    String login;
    public User(Role role, String password, String login) {
        setRole(role);
        setPassword(password);
        setLogin(login);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        if (role == null) throw new IllegalArgumentException("Role cannot be null");
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be null or blank");
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null || login.isBlank())
            throw new IllegalArgumentException("Login cannot be null or blank");
        this.login = login;
    }

    public boolean canDo(Right right) {
        return (right.getNeededRight() & role.getRight()) != 0;
    }
}
