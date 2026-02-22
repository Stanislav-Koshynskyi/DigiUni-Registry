package ui.pages;

import entity.Right;
import entity.Role;
import entity.User;
import exception.UserNotFoundException;
import exception.WrongPasswordException;
import security.AuthService;
import service.ServiceUserInterface;
import ui.*;

import java.io.Console;
import java.util.List;

public class UserPage extends BasePage {
    AuthService authService;
    ServiceUserInterface serviceUser;
    InputReader inputReader;

    public UserPage(Console console, AuthService authService, ServiceUserInterface serviceUser,
                    InputReader inputReader) {
        super(console);
        this.authService = authService;
        this.serviceUser = serviceUser;
        this.inputReader = inputReader;
    }

    /*
register/login for unlogged user
logout for logged user
view all user for admin
 */
    @Override
    public String getTitle() {
        return "User Page";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Login", Right.GUEST_ONLY,this :: login ),
                new MenuItem("Register", Right.GUEST_ONLY,this :: register ),
                new MenuItem("Logout", Right.LOGGED_IN, this :: logout ),
                new MenuItem("Show all user", Right.ADMIN_ONLY, this::showAllUsers )
        );
    }
    private Page login(){
        String username = inputReader.readString("Enter username: ");
        String password = inputReader.readPassword("Enter password: ");
        try {
            authService.login(username, password);
        }catch (UserNotFoundException e){
            System.out.println("User not found");
            return this;
        }catch (WrongPasswordException e){
            System.out.println("Wrong password");
            return this;
        }
        System.out.println("Logged in successfully");
        return this;
    }
    private Page register(){
        String username = inputReader.readString("Enter username: ");
        String password = inputReader.readPassword("Enter password: ");
        User user = new User(Role.USER, password, username);
        try {
            // або тут або в сервісі(краще в сервісі) додати перевірку ідентичності логіна
            serviceUser.save(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return this;
        }
        System.out.println("User registered successfully");
        return this;
    }
    private Page logout(){
        authService.logout();
        return this;
    }
    private Page showAllUsers(){
        List<User> users = serviceUser.findAllUsers();
        for (User user : users){
            System.out.println("id - " + user.getId() + " " + user);
        }
        return this;
    }

}
