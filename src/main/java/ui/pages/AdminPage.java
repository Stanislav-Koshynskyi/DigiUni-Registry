package ui.pages;

import entity.Right;
import entity.Role;
import entity.User;
import entity.deleteCriteria.UserDelete;
import entity.find_criteria.UserFind;
import service.ServiceUser;
import service.ServiceUserInterface;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;

import java.util.List;
import java.util.Optional;

public class AdminPage extends BasePage {
    private final ServiceUserInterface serviceUser;

    public AdminPage(InputReader inputReader, ServiceUserInterface serviceUser) {
        super(inputReader);
        this.serviceUser = serviceUser;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Show all user", Right.ADMIN_ONLY, this::showAllUsers),
                new MenuItem("Edit role", Right.ADMIN_ONLY, this::editRole),
                new MenuItem("Delete user", Right.ADMIN_ONLY, this::deleteUser)
        );
    }

    private Page deleteUser() {
        Optional<User> userOptional = findUser();
        if (userOptional.isEmpty()) {
            System.out.println("No user found");
        } else {
            User user = userOptional.get();
            UserDelete userDelete = inputReader.readChoose(
                    List.of(UserDelete.values()), "Choose how delete");
            switch (userDelete) {
                case HARD -> {
                    user.setRole(Role.DELETED);
                    serviceUser.delete(user);
                }
                case SOFT -> {
                    user.setRole(Role.DELETED);
                }
            }
            System.out.println("User deleted successfully, deleted type: " + userDelete);
        }
        return this;
    }

    private Page editRole() {
        Optional<User> userOptional = findUser();
        if (userOptional.isEmpty()) {
            System.out.println("No user found");
        } else {
            User user = userOptional.get();
            System.out.println("Current user role: " + user.getRole());
            Role newRole = inputReader.readChoose(List.of(Role.ADMIN, Role.MODERATOR, Role.USER), "Select a new role");
            user.setRole(newRole);
            System.out.println("Role for " + user.getLogin() + " changed successfully, new role: " + user.getRole());
        }
        return this;
    }

    private Optional<User> findUser() {
        UserFind find = inputReader.readChoose(List.of(UserFind.values()), "Choose criteria");
        switch (find) {
            case NAME:
                String login = inputReader.readString("Enter user name");
                return serviceUser.findUserByLogin(login);
            case ROLE:
                Role role = inputReader.readChoose(List.of(Role.values()), "Select role");
                return Optional.ofNullable(inputReader.readChoose(serviceUser.findByRole(role), "Select User"));
            case null, default:
                return Optional.empty();
        }
    }

    @Override
    public String getTitle() {
        return "Admin Panel";
    }

    private Page showAllUsers() {
        List<User> users = serviceUser.findAllUsers();
        for (User user : users) {
            System.out.println("id - " + user.getId() + " " + user);
        }
        return this;
    }
}
