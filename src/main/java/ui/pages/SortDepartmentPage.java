package ui.pages;

import entity.Department;
import entity.Right;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;

import java.util.Comparator;
import java.util.List;

public class SortDepartmentPage extends BasePage {
    private List<Department> department;
    public SortDepartmentPage(InputReader inputReader, List<Department> department) {
        super(inputReader);
        this.department = department;
    }

    public String getTitle() {
        return "Sort Departments";
    }

    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Sort department by name", Right.FIND, this::departmentSortByName),
                new MenuItem("Sort department by short name", Right.FIND, this::departmentSortByShortName),
                new MenuItem("Sort department by unique code", Right.FIND, this::departmentSortByUniqueCode)
        );
    }

    private Page departmentSortByUniqueCode() {
        department = department.stream().sorted(Comparator.comparing(Department::getUniqueCode)).toList();
        return showDepartments();
    }

    private Page departmentSortByShortName() {
        department =  department.stream().sorted(Comparator.comparing(Department::getShortName)).toList();
        return showDepartments();
    }

    private Page departmentSortByName() {
        department =  department.stream().sorted(Comparator.comparing(Department::getName)).toList();
        return showDepartments();
    }

    private Page showDepartments() {
        for (Department department : department)
            inputReader.println("id - " + department.getId() + ", " + department);
        return this;
    }
}
