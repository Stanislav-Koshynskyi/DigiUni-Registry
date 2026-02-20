package ui;

import java.util.List;

public abstract class BasePage implements Page {
    private final List<MenuItem> menuItems;

    public BasePage(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
