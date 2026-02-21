package ui;

import entity.Role;
import entity.User;
import security.AuthService;
import security.MethodFilter;
import util.Pretier;
import util.Reader;

import java.io.Console;
import java.util.List;
import java.util.Stack;

public class PageDisplay {
    // потім це перенести в sessionInfo(клас де зарашній юзер і стан логіна)
    private final AuthService authService;
    private final Console console;
    Stack<Page> history = new Stack<>();
    public PageDisplay(Console console, AuthService authService) {
        this.console = console;
        this.authService = authService;
    }
    public void start(Page startPage){
        System.out.println(Pretier.printLogo());
        history.push(startPage);
        display();
    }
    private void display() {
        while (true) {
            if (history.isEmpty()) break;
            Page currentPage = history.peek();
            List<MenuItem> itemToShow = MethodFilter.filterItems(currentPage,
                    authService.getCurrentUser());
            System.out.println(Pretier.wrapInFrame(currentPage.getTitle()));
            for (int i = 1; i <= itemToShow.size(); i++) {
                System.out.println(i + " - " + itemToShow.get(i - 1).getLabel());
            }
            System.out.println("0 - exit");
            int choose;
            while (true) {
                choose = Reader.readInt(console, "choose :");
                if (choose >= 0 && choose <= itemToShow.size()) break;
            }
            if (choose == 0)
                history.pop();
            else{
                Page newPage = (itemToShow.get(choose-1).execute());
                if (!newPage.equals(history.peek())) {
                    history.push(newPage);
                }
                else{
                    console.readPassword("Continue");
                }
            }

        }
        System.out.println("Goodbye!");
    }
}
