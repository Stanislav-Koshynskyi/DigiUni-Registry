package ui.pages;

import entity.Department;
import entity.Right;
import entity.StudentGroup;
import exception.EntityInUseException;
import service.ServiceStudentGroupInterface;
import ui.*;
import ui.finders.DepartmentFinderInterface;
import ui.finders.StudentGroupFinder;
import ui.finders.StudentGroupFinderInterface;
import util.Annotations;
import util.PagerBuilder;

import java.io.Console;
import java.util.List;
import java.util.Optional;

public class StudentGroupPage extends BasePage {
    private final ServiceStudentGroupInterface serviceStudentGroup;
    private final DepartmentFinderInterface departmentFinder;
    private final StudentGroupFinderInterface studentGroupFinder;
    private final PagerBuilder pagerBuilder;

    public StudentGroupPage(ServiceStudentGroupInterface serviceStudentGroup, InputReader inputReader,
                            DepartmentFinderInterface departmentFinder, StudentGroupFinderInterface studentGroupFinder, PagerBuilder pagerBuilder) {
        super(inputReader);
        this.serviceStudentGroup = serviceStudentGroup;
        this.departmentFinder = departmentFinder;
        this.studentGroupFinder = studentGroupFinder;
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "Student Group";
    }

//    @Override
//    public List<MenuItem> getMenuItems() {
//        return List.of(
//                new MenuItem("Create student group", Right.ADD, this::createStudentGroup),
//                new MenuItem("Edit student group", Right.EDIT, this::editStudentGroup),
//                new MenuItem("Delete student group", Right.DELETE, this::deleteStudentGroup),
//                new MenuItem("Show all student group", Right.FIND, this::showStudentGroup),
//                new MenuItem("Find student group", Right.FIND, pagerBuilder::getStudentGroupFindPage)
//        );
//    }
    @Annotations(name = "Find student group", right = Right.FIND, order = 5)
    private Page findStudentGroup() {
        return pagerBuilder.getStudentGroupFindPage(inputReader);
    }
    @Annotations(name = "Create student group", right = Right.ADD, order = 1)
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

    @Annotations(name = "Edit student group", right = Right.EDIT, order = 2)
    private Page editStudentGroup() {
        Optional<StudentGroup> optionalGroup = studentGroupFinder.findAndSelect();
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
    @Annotations(name = "Delete student group", right = Right.DELETE, order = 3)
    private Page deleteStudentGroup() {
        Optional<StudentGroup> studentGroup = studentGroupFinder.findAndSelect();
        if (studentGroup.isPresent()) {
            try {
                StudentGroup group = studentGroup.get();
                serviceStudentGroup.delete(group.getId());
            }catch (IllegalArgumentException e) {
                System.out.println("Deleting error");
            }catch (EntityInUseException e) {
                System.out.println(e.getMessage());
            }
        }
        return this;
    }
    @Annotations(name = "Show all student groups", right = Right.FIND, order = 4)
    private Page showStudentGroup() {
        for (StudentGroup group : serviceStudentGroup.findAll()) {
            System.out.println("ID: " + group.getId() + ", " + group);
        }
        return this;
    }
}

