package ui.pages;

import entity.Right;
import entity.Student;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;
import ui.finders.StudentFinderInterface;

import java.util.List;
import java.util.Optional;

public class FindStudentPage extends BasePage {
    private final StudentFinderInterface studentFinder;
    public FindStudentPage(InputReader inputReader, StudentFinderInterface studentFinder) {
        super(inputReader);
        this.studentFinder = studentFinder;
    }

    @Override
    public String getTitle() {
        return "Find Student";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("By org structure", Right.FIND, this::findByOrgStructure),
                new MenuItem("By ids", Right.FIND, this::findByIDs),
                new MenuItem("By student status", Right.FIND, this::findByStudentStatus),
                new MenuItem("By form of education", Right.FIND, this::findByFormOfEducation),
                new MenuItem("By course", Right.FIND, this::findByCourse),
                new MenuItem("By contact", Right.FIND, this::findByContact),
                new MenuItem("By name", Right.FIND, this::findByName),
                new MenuItem("By surname", Right.FIND, this::findBySurname),
                new MenuItem("By patronymic", Right.FIND, this::findByPatronymic),
                new MenuItem("Advanced search", Right.FIND, this::advancedSearch)
        );
    }
    private void printStudents(List<Student> students) {
        if  (students.isEmpty()) {
            System.out.println("No students found");
        }
        else{
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
    private Page findByOrgStructure(){
        printStudents(studentFinder.findByOrgStructure());
        return this;
    }
    private Page findByIDs(){
        printStudents(studentFinder.findByIds());
        return this;
    }
    private Page findBySurname(){
        printStudents(studentFinder.findBySurname());
        return this;
    }
    private Page findByPatronymic(){
        printStudents(studentFinder.findByPatronymic());
        return this;
    }
    private Page findByName(){
        printStudents(studentFinder.findByName());
        return this;
    }
    private Page findByCourse(){
        printStudents(studentFinder.findByCourse());
        return this;
    }
    private Page findByContact(){
        Optional<Student> student = studentFinder.findByContact();
        if (student.isPresent()) {
            printStudents(List.of(student.get()));
        }
        else{
            printStudents(List.of());
        }
        return this;
    }
    private Page advancedSearch(){
        printStudents(studentFinder.advancedSearch());
        return this;
    }
    private Page findByStudentStatus(){
        printStudents(studentFinder.findByStudentStatus());
        return this;
    }
    private Page findByFormOfEducation(){
        printStudents(studentFinder.findByFormOfEducation());
        return this;
    }
}