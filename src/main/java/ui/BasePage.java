package ui;

import java.io.Console;
import java.util.List;

public abstract class BasePage implements Page {
    private Console console;
    public BasePage(Console console) {
        this.console = console;
    }
    public abstract List<MenuItem> getMenuItems();
}
