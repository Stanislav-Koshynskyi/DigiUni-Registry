package ui;

import security.AuthService;
import security.MethodFilter;
import util.Pretier;

import java.io.Console;
import java.util.List;
import java.util.Stack;

public class PageDisplay {
    private final AuthService authService;
    private final InputReader inputReader;
    Stack<Page> history = new Stack<>();
    public PageDisplay(AuthService authService, InputReader inputReader) {
        this.authService = authService;
        this.inputReader = inputReader;
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
                choose = inputReader.readInt("choose :");
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
                    inputReader.readPassword("Continue");
                }
            }

        }
        System.out.println("Goodbye!");
    }
}
