package ui;

import entity.Role;
import entity.User;
import util.MethodFilter;
import util.Reader;

import java.io.Console;
import java.util.List;
import java.util.Stack;

public class PageDisplay {
    // потім це перенести в sessionInfo(клас де зарашній юзер і стан логіна)
    User currentUser = new User(Role.USER, "asd","asd");
    private Console console;
    Stack<Page> history = new Stack<>();
    public PageDisplay(Console console) {
        this.console = console;
    }
    public void start(Page startPage){
        history.push(startPage);
        display();
    }
    private void display() {
        while (true) {
            if (history.isEmpty()) break;
            Page currentPage = history.peek();
            List<MenuItem> itemToShow = MethodFilter.filterItems(currentPage, currentUser);
            System.out.println(currentPage.getTitle());
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
                if (!newPage.equals(history.peek())) history.push(newPage);
            }

        }
        System.out.println("Goodbye!");
    }
}
