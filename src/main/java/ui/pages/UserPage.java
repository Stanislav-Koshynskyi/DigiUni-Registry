package ui.pages;

import entity.Right;
import entity.Role;
import entity.User;
import exception.UserDeletedException;
import exception.UserNotFoundException;
import exception.WrongPasswordException;
import security.AuthService;
import service.ServiceUserInterface;
import ui.*;
import util.Annotations;
import util.PagerBuilder;

import java.io.Console;
import java.util.List;

public class UserPage extends BasePage {
    AuthService authService;
    ServiceUserInterface serviceUser;
    PagerBuilder pagerBuilder;

    public UserPage(AuthService authService, ServiceUserInterface serviceUser,
                    InputReader inputReader, PagerBuilder pagerBuilder) {
        super(inputReader);
        this.authService = authService;
        this.serviceUser = serviceUser;
        this.inputReader = inputReader;
        this.pagerBuilder = pagerBuilder;
    }

    /*
register/login for unlogged user
logout for logged user
view all user for admin
 */
    @Override
    public String getTitle() {
        return "Account";
    }

//    @Override
//    public List<MenuItem> getMenuItems() {
//        return List.of(
//                new MenuItem("Login", Right.GUEST_ONLY,this :: login ),
//                new MenuItem("Register", Right.GUEST_ONLY,this :: register ),
//                new MenuItem("Logout", Right.LOGGED_IN, this :: logout ),
//                new MenuItem("Admin Panel", Right.ADMIN_ONLY, pagerBuilder::getAdminPage)
//        );
//    }
    @Annotations(name = "Admin Panel", right = Right.ADMIN_ONLY, order = 4)
    private Page adminPanel() {
        return pagerBuilder.getAdminPage();
    }
    @Annotations(name = "Login", right = Right.GUEST_ONLY, order = 1)
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
        catch (UserDeletedException e){
            System.out.println("Current user has been deleted, try other or create a new one");
            return this;
        }
        System.out.println("Logged in successfully");
        return this;
    }
    @Annotations(name = "Register", right = Right.GUEST_ONLY, order = 2)
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
    @Annotations(name = "Logout", right = Right.LOGGED_IN, order = 3)
    private Page logout(){
        authService.logout();
        return this;
    }

}
