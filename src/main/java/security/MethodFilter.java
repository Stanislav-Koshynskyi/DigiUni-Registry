package security;

import ui.MenuItem;
import ui.Page;

import java.util.ArrayList;
import java.util.List;
import entity.User;
public class MethodFilter {
    public static List<MenuItem> filterItems(Page page, User user){
        List<MenuItem> allMenuItems = page.getMenuItems();
        List<MenuItem> filteredMenuItems = new ArrayList<>();
        for (MenuItem menuItem : allMenuItems) {
            if (user.canDo(menuItem.getRightNeeded()))
                filteredMenuItems.add(menuItem);
        }
        return filteredMenuItems;
    }
}
