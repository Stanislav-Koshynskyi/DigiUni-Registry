package ui;

import java.io.Console;
import java.util.List;

public abstract class BasePage implements Page {
    protected InputReader inputReader;
    public BasePage(InputReader inputReader) {
        this.inputReader = inputReader;
    }
    public abstract List<MenuItem> getMenuItems();
}
