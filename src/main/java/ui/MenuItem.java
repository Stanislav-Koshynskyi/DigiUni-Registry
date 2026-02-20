package ui;

import entity.Right;
import entity.User;

public class MenuItem {
    private final String label;
    private final MenuTask task;
    private final Right rightNeeded;
    public MenuItem(String label, Right rightNeeded,  MenuTask task) {
        this.label = label;
        this.task = task;
        this.rightNeeded = rightNeeded;
    }
    public String getLabel() {
        return label;
    }
    public Page execute() {
        return task.execute();
    }
}
