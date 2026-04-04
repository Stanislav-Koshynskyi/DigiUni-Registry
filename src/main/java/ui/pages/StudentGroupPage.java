package ui.pages;

import entity.Department;
import entity.Right;
import entity.StudentGroup;
import service.ServiceDepartmentInterface;
import service.ServiceStudentGroupInterface;
import ui.*;
import ui.finders.DepartmentFinderInterface;
import util.PagerBuilder;

import java.io.Console;
import java.util.List;
import java.util.Optional;

public class StudentGroupPage extends BasePage {
    private final ServiceStudentGroupInterface serviceStudentGroup;
    private final DepartmentFinderInterface departmentFinder;
    private final PagerBuilder pagerBuilder;

    public StudentGroupPage(ServiceStudentGroupInterface serviceStudentGroup, InputReader inputReader,
                            DepartmentFinderInterface departmentFinder, PagerBuilder pagerBuilder) {
        super(inputReader);
        this.serviceStudentGroup = serviceStudentGroup;
        this.departmentFinder = departmentFinder;
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "Student Group";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Create student group", Right.ADD, this::createStudentGroup),
                new MenuItem("Edit student group", Right.EDIT, this::editStudentGroup),
                new MenuItem("Delete student group", Right.DELETE, this::deleteStudentGroup),
                new MenuItem("Show all student group", Right.FIND, this::showStudentGroup),
                new MenuItem("Find student group", Right.FIND, pagerBuilder::getStudentGroupFindPage)
        );
    }
    private Page createStudentGroup() {
        String name = inputReader.readString("Enter group name: ");

        Optional<Department> department = departmentFinder.findAndSelect();
        if (department.isEmpty()) {
            System.out.println("Department not found");
            System.out.println("Group not created");
            return this;
        }


        StudentGroup group = new StudentGroup(name, department.get());
        serviceStudentGroup.create(group);
        return this;
    }

    private Page editStudentGroup() {
        Long id = inputReader.readLong("Enter group id to edit: ");
        Optional<StudentGroup> optionalGroup = serviceStudentGroup.findById(id);

        if (optionalGroup.isEmpty()) {
            System.out.println("Group not found!");
            return this;
        }
        StudentGroup group = optionalGroup.get();

        String name = inputReader.readProbablyBlank("Enter group name: ");
        if (!name.isBlank()) {
            group.setName(name);
        }

        serviceStudentGroup.update(group);
        return this;
    }

    private Page deleteStudentGroup() {
        Long id = inputReader.readLong("Enter group id to delete: ");
        // треба додати перевірку на існування
        serviceStudentGroup.delete(id);
        return this;
    }

    private Page showStudentGroup() {
        for (StudentGroup group : serviceStudentGroup.findAll()) {
            System.out.println("ID: " + group.getId() + group);
        }
        return this;
    }
}

