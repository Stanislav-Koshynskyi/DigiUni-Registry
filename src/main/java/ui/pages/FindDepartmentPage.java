package ui.pages;

import entity.Department;
import entity.Right;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;
import ui.finders.DepartmentFinder;
import ui.finders.DepartmentFinderInterface;

import java.util.List;
import java.util.Optional;

public class FindDepartmentPage extends BasePage {
    private DepartmentFinderInterface departmentFinder;

    public FindDepartmentPage(InputReader inputReader, DepartmentFinderInterface departmentFinder) {
        super(inputReader);
        this.departmentFinder = departmentFinder;
    }

    @Override
    public String getTitle() {
        return "Find Department";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("By unique code", Right.FIND, this::findByUniqueCode),
                new MenuItem("By name", Right.FIND, this::findByName),
                new MenuItem("By short name", Right.FIND, this::findByShortName),
                new MenuItem("By faculty", Right.FIND, this::findByFaculty),
                new MenuItem("By university", Right.FIND, this::findByUniversity),
                new MenuItem("Advanced", Right.FIND, this::advancedSearch)

        );
    }


    private Page findByUniqueCode() {
        return new SortDepartmentPage(inputReader, departmentFinder.findByUniqueCode());
    }
    private void printDepartments(List<Department> departments) {
        if (departments.isEmpty()) {
            inputReader.println("No departments found");
        }
        else{
            for (Department department : departments) {
                inputReader.println(department);
            }
        }
    }
    private Page findByName() {
        printDepartments(departmentFinder.findByName());
        return this;
    }
    private Page findByShortName() {
        printDepartments(departmentFinder.findByShortName());
        return this;
    }
    private Page findByFaculty() {
        printDepartments(departmentFinder.findByFaculty());
        return this;
    }
    private Page findByUniversity() {
        printDepartments(departmentFinder.findByUniversity());
        return this;
    }
    private Page advancedSearch() {
        printDepartments(departmentFinder.advancedSearch());
        return this;
    }
}
