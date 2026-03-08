package ui.pages;

import entity.Right;
import entity.StudentGroup;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;
import ui.finders.StudentGroupFinderInterface;

import java.util.List;

public class FindStudentGroupPage extends BasePage {
    private final StudentGroupFinderInterface studentGroupFinder;
    public FindStudentGroupPage(InputReader inputReader, StudentGroupFinderInterface studentGroupFinder) {
        super(inputReader);
        this.studentGroupFinder = studentGroupFinder;
    }

    @Override
    public String getTitle() {
        return "Find student group";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("By name", Right.FIND, this::findByName),
                new MenuItem("By university", Right.FIND, this::findByUniversity),
                new MenuItem("By department", Right.FIND, this::findByDepartment),
                new MenuItem("By Faculty", Right.FIND, this::findByFaculty)
        );
    }
    private Page findByName (){
        printStudentGroup(studentGroupFinder.findByName());
        return this;
    }
    private Page findByUniversity(){
        printStudentGroup(studentGroupFinder.findByUniversity());
        return this;
    }
    private  Page findByDepartment(){
        printStudentGroup(studentGroupFinder.findByDepartment());
        return this;
    }
    private  Page findByFaculty(){
        printStudentGroup(studentGroupFinder.findByFaculty());
        return this;
    }
    private void printStudentGroup(List<StudentGroup> studentGroups) {
        if (studentGroups.isEmpty()) {
            System.out.println("No departments found");
        }
        else{
            for (StudentGroup studentGroup : studentGroups) {
                System.out.println(studentGroup);
            }
        }
    }
}
/*
NAME("By name"),
    UNIVERSITY("By university"),
    DEPARTMENT("By department"),
    FACULTY("By faculty"),

 */
