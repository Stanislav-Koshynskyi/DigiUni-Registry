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
        List<Student> student = studentFinder.findByOrgStructure();
        return new SortStudentPage(inputReader, student);
    }
    private Page findByIDs(){
        printStudents(studentFinder.findByIds());
        return this;
    }
    private Page findBySurname(){
        List<Student> student = studentFinder.findBySurname();
        return new SortStudentPage(inputReader, student);
    }
    private Page findByPatronymic(){
        List<Student> student = studentFinder.findByPatronymic();
        return new SortStudentPage(inputReader, student);
    }
    private Page findByName(){
        List<Student> student = studentFinder.findByName();
        return new SortStudentPage(inputReader, student);
    }
    private Page findByCourse(){
        List<Student> student = studentFinder.findByCourse();
        return new SortStudentPage(inputReader, student);
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
        List<Student> student = studentFinder.advancedSearch();
        return new SortStudentPage(inputReader, student);
    }
    private Page findByStudentStatus(){
        List<Student> student = studentFinder.findByStudentStatus();
        return new SortStudentPage(inputReader, student);
    }
    private Page findByFormOfEducation(){
        List<Student> student = studentFinder.findByFormOfEducation();
        return new SortStudentPage(inputReader, student);
    }
}