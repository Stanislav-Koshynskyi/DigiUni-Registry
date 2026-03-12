package ui.pages;

import entity.Right;
import entity.Student;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;

import java.util.Comparator;

import java.util.List;

public class SortStudentPage extends BasePage {
    private List<Student> student;
    public  SortStudentPage(InputReader inputReader, List<Student> students) {
        super(inputReader);
        this.student = students;
    }

    public String getTitle() {
        return "Sort Student";
    }

    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Sort students by course", Right.FIND, this::studentSortedByCourse),
                new MenuItem("Sort students by name", Right.FIND, this::studentSortedByName),
                new MenuItem("Sort students by surname", Right.FIND, this::studentSortedBySurname),
                new MenuItem("Sort students by patronymic", Right.FIND, this::studentSortedByPatronymic),
                new MenuItem("Show all students without sorting", Right.FIND, this::showStudent)
        );
    }

    private Page studentSortedByCourse() {
        student = student.stream().sorted(Comparator.comparing(Student::getCourse)).toList();
        showStudent();
        return this;
    }

    private Page studentSortedByName() {
        student = student.stream().sorted(Comparator.comparing(s -> s.getFullName().name())).toList();
        showStudent();
        return this;
    }

    private Page studentSortedBySurname() {
        student = student.stream().sorted(Comparator.comparing(s -> s.getFullName().surname())).toList();
        showStudent();
        return this;
    }

    private Page studentSortedByPatronymic() {
        student = student.stream().sorted(Comparator.comparing(s -> s.getFullName().patronymic())).toList();
        showStudent();
        return this;
    }

    private Page showStudent() {
        for (Student student : student)
            System.out.println("id - " + student.getId() + ", " + student);
        return this;
    }
}
